package tfw.immutable.ila.objectila;

import java.io.IOException;

public final class TestCloseObjectIla implements ObjectIla<Object> {
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
    public void get(Object[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ObjectIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
// AUTO GENERATED FROM TEMPLATE
