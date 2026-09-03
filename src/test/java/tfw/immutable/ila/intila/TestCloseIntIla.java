package tfw.immutable.ila.intila;

import java.io.IOException;

public final class TestCloseIntIla implements IntIla {
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
    public void get(int[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test IntIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
// AUTO GENERATED FROM TEMPLATE
