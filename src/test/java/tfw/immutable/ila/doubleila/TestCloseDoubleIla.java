package tfw.immutable.ila.doubleila;

import java.io.IOException;

public final class TestCloseDoubleIla implements DoubleIla {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test DoubleIla");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(double[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test DoubleIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
