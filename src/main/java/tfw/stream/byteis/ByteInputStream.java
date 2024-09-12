package tfw.stream.byteis;

import java.io.IOException;

public interface ByteInputStream {
    public long available() throws IOException;

    public void close() throws IOException;

    public int read(byte[] array) throws IOException;

    public int read(byte[] array, int offset, int length) throws IOException;

    public long skip(long n) throws IOException;
}
