package tfw.immutable.iis.booleaniis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a boolean Input Stream (BooleanIis).
 */
public interface BooleanIis extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the booleans.
     * @param offset into the array to put the first boolean.
     * @param length the number of booleans to put into the array.
     * @return the number of booleans read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the booleans.
     */
    int read(boolean[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of booleans to skip.
     * @return the number of booleans skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the booleans.
     */
    long skip(long n) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
