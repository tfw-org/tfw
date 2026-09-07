package tfw.immutable.ila.longila;

import java.io.IOException;

public final class TestCloseLongIla implements LongIla {
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
    public void get(long[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test LongIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
// AUTO GENERATED FROM TEMPLATE
