package tfw.stream.byteis;

import tfw.immutable.DataInvalidException;

public interface ByteInputStream {
    public long available() throws DataInvalidException;

    public void close() throws DataInvalidException;

    public int read(byte[] array) throws DataInvalidException;

    public int read(byte[] array, int offset, int length) throws DataInvalidException;

    public long skip(long n) throws DataInvalidException;
}
