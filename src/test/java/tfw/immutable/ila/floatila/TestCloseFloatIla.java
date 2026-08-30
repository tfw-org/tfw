package tfw.immutable.ila.floatila;

import java.io.IOException;

public final class TestCloseFloatIla implements FloatIla {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test FloatIla");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(float[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test FloatIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
