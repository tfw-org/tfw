package tfw.immutable.ila.shortila;

import java.io.IOException;

public final class TestCloseShortIla implements ShortIla {
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
    public void get(short[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ShortIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
