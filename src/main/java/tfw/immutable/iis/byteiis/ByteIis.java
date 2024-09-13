package tfw.immutable.iis.byteiis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a byte Input Stream (ByteIis).
 */
public interface ByteIis extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the bytes.
     * @param offset into the array to put the first byte.
     * @param length the number of bytes to put into the array.
     * @return the number of bytes read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the bytes.
     */
    int read(byte[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of bytes to skip.
     * @return the number of bytes skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the bytes.
     */
    long skip(long n) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
