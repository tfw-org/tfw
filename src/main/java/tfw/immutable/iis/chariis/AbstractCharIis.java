package tfw.immutable.iis.chariis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements CharIis and provides
 * some common parameter validation.
 */
public abstract class AbstractCharIis implements CharIis {
    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the chars.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the chars.
     * @param offset into the array to put the first char.
     * @param length of chars to put into the array.
     * @return the number of chars put into the array.
     * @throws IOException if something happens while trying to read the chars.
     */
    protected abstract int readImpl(char[] array, int offset, int length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of chars to skip.
     * @return the number of chars skipped.
     * @throws IOException if something happens while trying to skip the chars.
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
    public final int read(final char[] array, final int offset, final int length) throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");

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
