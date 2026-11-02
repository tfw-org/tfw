// booleanila,byteila,charila,doubleila,floatila,intila,longila,objectila,shortila
package ${PACKAGE};

import java.io.IOException;

public final class TestClose${NAME}Ila implements ${NAME}Ila${TEMPLATE} {
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
    public void get(${TYPE}[] array, int arrayOffset, long ilaStart, int length) throws IOException {
        throw new IOException("Close Test ${NAME}Ila");
    }

    public int getNumberOfCloses() {
        return numberOfCloses;
    }
}
