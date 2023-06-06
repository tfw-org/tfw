package tfw.stream.doubleis;

import tfw.immutable.DataInvalidException;

public interface DoubleInputStream {
    public long available() throws DataInvalidException;

    public void close() throws DataInvalidException;

    public int read(double[] array) throws DataInvalidException;

    public int read(double[] array, int offset, int length) throws DataInvalidException;

    public long skip(long n) throws DataInvalidException;
}
