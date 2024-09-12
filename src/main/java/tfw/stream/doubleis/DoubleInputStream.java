package tfw.stream.doubleis;

import java.io.IOException;

public interface DoubleInputStream {
    public long available() throws IOException;

    public void close() throws IOException;

    public int read(double[] array) throws IOException;

    public int read(double[] array, int offset, int length) throws IOException;

    public long skip(long n) throws IOException;
}
