package tfw.immutable.iis.floatiis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a float Input Stream (FloatIis).
 */
public interface FloatIis extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the floats.
     * @param offset into the array to put the first float.
     * @param length the number of floats to put into the array.
     * @return the number of floats read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the floats.
     */
    int read(float[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of floats to skip.
     * @return the number of floats skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the floats.
     */
    long skip(long n) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
