package tfw.immutable.iis.bitiis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a bit Input Stream (BitIis).
 */
public interface BitIis extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the bits.
     * @param offset into the array to put the first bit.
     * @param length the number of bits to put into the array.
     * @return the number of bits read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the bits.
     */
    int read(long[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of bits to skip.
     * @return the number of bits skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the bits.
     */
    long skip(long n) throws IOException;
}
