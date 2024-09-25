package tfw.immutable.iis;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;

public abstract class AbstractIis {
    private static final BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the bytes.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of bytes to skip.
     * @return the number of bytes skipped.
     * @throws IOException if something happens while trying to skip the bytes.
     */
    protected abstract long skipImpl(long n) throws IOException;

    protected boolean closed = false;

    public final void close() throws IOException {
        if (!closed) {
            closed = true;

            closeImpl();
        }
    }

    protected final BigInteger readCheck(final Object array, final int offset, final int length) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");

        if (closed) {
            return NEGATIVE_ONE;
        }
        if (length == 0) {
            return ZERO;
        }

        return null;
    }

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
