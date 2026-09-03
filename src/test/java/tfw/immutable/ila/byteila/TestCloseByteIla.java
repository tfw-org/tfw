package tfw.immutable.ila.byteila;

import java.io.IOException;

public final class TestCloseByteIla implements ByteIla {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        return 11L;
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(byte[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ByteIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
