package tfw.immutable.iis.objectiis;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines a Object Input Stream (ObjectIis).
 */
public interface ObjectIis<T> extends Closeable {
    /**
     * This method is analogous to InputStream.read(byte[], int, int).
     *
     * @param array to put the Objects.
     * @param offset into the array to put the first Object.
     * @param length the number of Objects to put into the array.
     * @return the number of Objects read or -1 if at end of stream.
     * @throws IOException if there is a problem reading the Objects.
     */
    int read(T[] array, int offset, int length) throws IOException;

    /**
     * This method is analogous to InputStream.skip(long).
     *
     * @param n the number of Objects to skip.
     * @return the number of Objects skipped or -1 if at end of stream.
     * @throws IOException if there is a problem skipping the Objects.
     */
    long skip(long n) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
