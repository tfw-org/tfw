package tfw.immutable.iba.booleaniba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;

public abstract class AbstractBooleanIba implements BooleanIba {
    protected abstract void closeImpl() throws IOException;

    protected abstract BigInteger lengthImpl() throws IOException;

    protected abstract void getImpl(final boolean[] array, int arrayOffset, BigInteger ibaStart, int length)
            throws IOException;

    private boolean closed = false;

    @Override
    public final void close() throws IOException {
        if (!closed) {
            closed = true;
            closeImpl();
        }
    }

    @Override
    public final BigInteger length() throws IOException {
        if (closed) {
            throw new IllegalStateException("This BooleanIba is closed!");
        }

        return lengthImpl();
    }

    @Override
    public final void get(boolean[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
        if (closed) {
            throw new IllegalStateException("This BooleanIba is closed!");
        }

        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffset, 0, "offset");
        Argument.assertLessThan(arrayOffset, MAX_BITS_IN_ARRAY, "arrayOffset", "MAX_BITS_IN_ARRAY");
        Argument.assertLessThan(arrayOffset + (long) length, array.length * (long) Long.SIZE, "offset", "array.length");
        Argument.assertGreaterThanOrEqualTo(ibaStart, BigInteger.ZERO, "ibaStart");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertLessThan(length, MAX_BITS_IN_ARRAY, "length", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ibaStart.add(BigInteger.valueOf(length)), length(), "start+length", "Iba.length()");

        if (length == 0) {
            return;
        }

        getImpl(array, arrayOffset, ibaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
