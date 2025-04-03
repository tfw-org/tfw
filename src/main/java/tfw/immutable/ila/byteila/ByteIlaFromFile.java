package tfw.immutable.ila.byteila;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfw.check.Argument;

public final class ByteIlaFromFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteIlaFromFile.class);

    private ByteIlaFromFile() {}

    public static ByteIla create(File file) {
        Argument.assertNotNull(file, "file");

        if (!file.exists()) throw new IllegalArgumentException("file does not exist!");
        if (!file.canRead()) throw new IllegalArgumentException("file cannot be read!");

        return new ByteIlaImpl(file);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final File file;

        private RandomAccessFile raf = null;
        private TimerRunnable timerRunnable = null;

        private ByteIlaImpl(File file) {
            this.file = file;
        }

        @Override
        protected long lengthImpl() {
            return file.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            if (raf == null) {
                try {
                    raf = new RandomAccessFile(file, "r");
                } catch (FileNotFoundException fnfe) {
                    return;
                }
            }

            try {
                raf.seek(start);
                raf.readFully(array, offset, length);
            } catch (IOException ioe) {
                return;
            }

            if (timerRunnable == null) {
                timerRunnable = new TimerRunnable(this);

                Thread timerThread = new Thread(timerRunnable);
                timerThread.setDaemon(true);
                timerThread.setPriority(Thread.MIN_PRIORITY);
                timerThread.start();
            } else {
                timerRunnable.resetTimer();
            }
        }

        public synchronized void closeRAF() {
            try {
                raf.close();
            } catch (IOException ioe) {
                LOGGER.warn("Failed to close RandomAccessFile!", ioe);
            }

            raf = null;
            timerRunnable = null;
        }
    }

    private static class TimerRunnable implements Runnable {
        private boolean resetTimer = true;
        private ByteIlaImpl byteIlaImpl = null;

        public TimerRunnable(ByteIlaImpl byteIlaImpl) {
            if (byteIlaImpl == null) throw new IllegalArgumentException("byteIlaImpl == null not allowed!");

            this.byteIlaImpl = byteIlaImpl;
        }

        @Override
        public synchronized void run() {
            while (resetTimer) {
                resetTimer = false;

                try {
                    wait(10000);
                } catch (InterruptedException ie) {
                }
            }
            byteIlaImpl.closeRAF();
        }

        public synchronized void resetTimer() {
            resetTimer = true;
        }
    }
}
