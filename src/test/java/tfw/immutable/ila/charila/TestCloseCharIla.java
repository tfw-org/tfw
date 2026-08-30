package tfw.immutable.ila.charila;

import java.io.IOException;

public final class TestCloseCharIla implements CharIla {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test CharIla");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(char[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test CharIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
