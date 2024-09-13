package tfw.immutable.iis.objectiis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements ObjectIis and provides
 * some common parameter validation.
 */
public abstract class AbstractObjectIis<T> implements ObjectIis<T> {
    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the Objects.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the Objects.
     * @param offset into the array to put the first Object.
     * @param length of Objects to put into the array.
     * @return the number of Objects put into the array.
     * @throws IOException if something happens while trying to read the Objects.
     */
    protected abstract int readImpl(T[] array, int offset, int length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of Objects to skip.
     * @return the number of Objects skipped.
     * @throws IOException if something happens while trying to skip the Objects.
     */
    protected abstract long skipImpl(long n) throws IOException;

    private boolean closed = false;

    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;

            closeImpl();
        }
    }

    @Override
    public final int read(final T[] array, final int offset, final int length) throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(length, array.length - offset, "length", "array.length - offset");

        if (closed) {
            return -1;
        }
        if (length == 0) {
            return 0;
        }

        return readImpl(array, offset, length);
    }

    @Override
    public final long skip(final long n) throws IOException {
        if (closed) {
            return -1;
        }
        if (n < 1) {
            return 0;
        }

        return skipImpl(n);
    }
}
// AUTO GENERATED FROM TEMPLATE
