package tfw.immutable.iis.intiis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements IntIis and provides
 * some common parameter validation.
 */
public abstract class AbstractIntIis implements IntIis {
    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the ints.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the ints.
     * @param offset into the array to put the first int.
     * @param length of ints to put into the array.
     * @return the number of ints put into the array.
     * @throws IOException if something happens while trying to read the ints.
     */
    protected abstract int readImpl(int[] array, int offset, int length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of ints to skip.
     * @return the number of ints skipped.
     * @throws IOException if something happens while trying to skip the ints.
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
    public final int read(final int[] array, final int offset, final int length) throws IOException {
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
