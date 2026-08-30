package ${package};

import java.io.IOException;

public final class TestClose${ilaType} implements ${ilaType}<#if generic??><${generic}></#if> {
    private int numberOfCloses = 0;

    @Override
    public long length() throws IOException {
        throw new IOException("Close Test ${ilaType}");
    }

    @Override
    public void close() throws IOException {
        numberOfCloses++;
    }

    @Override
    public void get(${arrayType} array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ${ilaType}");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
