package tfw.immutable.iis.shortiis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a short Input Stream (ShortIis).
 */
public interface ShortIis extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the shorts.
     * @param offset into the array to put the first short.
     * @param length the number of shorts to put into the array.
     * @return the number of shorts read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the shorts.
     */
    int read(short[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of shorts to skip.
     * @return the number of shorts skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the shorts.
     */
    long skip(long n) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
