package tfw.immutable.ila.objectila;

import java.io.IOException;

public final class TestCloseObjectIla implements ObjectIla<String> {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test ObjectIla");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(String[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ObjectIla");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
