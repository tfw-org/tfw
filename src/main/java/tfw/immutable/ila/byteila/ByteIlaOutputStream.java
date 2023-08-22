package tfw.immutable.ila.byteila;

import java.io.IOException;
import java.io.OutputStream;
import tfw.check.Argument;

public class ByteIlaOutputStream extends OutputStream {
    private final OutputStream outputStream;

    private boolean streamClosed = false;

    public ByteIlaOutputStream(OutputStream outputStream) {
        Argument.assertNotNull(outputStream, "outputStream");

        this.outputStream = outputStream;
    }

    public void write(byte[] b) throws IOException {
        checkClosed();

        outputStream.write(b);
    }

    public void write(int i) throws IOException {
        checkClosed();

        outputStream.write(i);
    }

    public void write(byte[] b, int offset, int length) throws IOException {
        outputStream.write(b, offset, length);
    }

    public void writeByteIla(ByteIla byteIla, int bufferSize) throws IOException {
        checkClosed();

        int bytesToWrite = byteIla.length() > bufferSize ? bufferSize : (int) byteIla.length();
        byte[] b = new byte[bytesToWrite];

        for (long i = 0; bytesToWrite > 0; ) {
            byteIla.toArray(b, 0, i, bytesToWrite);
            write(b, 0, bytesToWrite);
            i += bytesToWrite;

            bytesToWrite = (byteIla.length() - i) > b.length ? b.length : (int) (byteIla.length() - i);
        }
    }

    public void flush() throws IOException {
        checkClosed();

        outputStream.flush();
    }

    public void close() throws IOException {
        checkClosed();

        outputStream.close();

        streamClosed = true;
    }

    private void checkClosed() {
        if (streamClosed) throw new IllegalStateException("Stream is closed!");
    }
}
