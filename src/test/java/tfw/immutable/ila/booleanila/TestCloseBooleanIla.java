package tfw.immutable.ila.booleanila;

import java.io.IOException;

public final class TestCloseBooleanIla implements BooleanIla {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test BooleanIla");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(boolean[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test BooleanIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
