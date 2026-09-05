package tfw.immutable.ila.byteila;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import tfw.check.Argument;

public final class ByteIlaFromFile {
    private ByteIlaFromFile() {}

    public static ByteIla create(File file) {
        return new ByteIlaImpl(file);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final File file;

        private RandomAccessFile raf = null;

        private ByteIlaImpl(File file) {
            Argument.assertNotNull(file, "file");

            if (!file.exists()) throw new IllegalArgumentException("file does not exist!");
            if (!file.canRead()) throw new IllegalArgumentException("file cannot be read!");

            this.file = file;
        }

        @Override
        protected long lengthImpl() {
            return file.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            if (raf == null) {
                raf = new RandomAccessFile(file, "r");
            }

            raf.seek(start);
            raf.readFully(array, offset, length);
        }

        @Override
        protected void closeImpl() throws IOException {
            raf.close();
        }
    }
}
