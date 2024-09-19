package tfw.immutable.iis.shortiis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements ShortIis and provides
 * some common parameter validation.
 */
public abstract class AbstractShortIis implements ShortIis {
    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the shorts.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the shorts.
     * @param offset into the array to put the first short.
     * @param length of shorts to put into the array.
     * @return the number of shorts put into the array.
     * @throws IOException if something happens while trying to read the shorts.
     */
    protected abstract int readImpl(short[] array, int offset, int length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of shorts to skip.
     * @return the number of shorts skipped.
     * @throws IOException if something happens while trying to skip the shorts.
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
    public final int read(final short[] array, final int offset, final int length) throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(length, array.length - (long) offset, "length", "array.length - offset");

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
