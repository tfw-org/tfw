package tfw.immutable.iba.bitiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;

public abstract class AbstractBitIba implements BitIba {
    protected abstract void closeImpl() throws IOException;

    protected abstract BigInteger lengthInBitsImpl() throws IOException;

    protected abstract void getImpl(long[] array, long arrayOffsetInBits, BigInteger ilaStartInBits, long lengthInBits)
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
    public final BigInteger lengthInBits() throws IOException {
        if (closed) {
            throw new IllegalStateException("This BitIla is closed!");
        }

        return lengthInBitsImpl();
    }

    @Override
    public final void get(long[] array, long arrayOffsetInBits, BigInteger ilaStartInBits, long lengthInBits)
            throws IOException {
        if (closed) {
            throw new IllegalStateException("This BitIla is closed!");
        }

        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "offset");
        Argument.assertLessThan(arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertLessThan(
                arrayOffsetInBits + lengthInBits, array.length * (long) Long.SIZE, "offset", "array.length");
        Argument.assertGreaterThanOrEqualTo(ilaStartInBits, BigInteger.ZERO, "start");
        Argument.assertNotLessThan(lengthInBits, 0, "length");
        Argument.assertLessThan(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ilaStartInBits.add(BigInteger.valueOf(lengthInBits)),
                lengthInBits(),
                "start+length",
                "Ila.lengthInBits()");

        if (lengthInBits == 0) {
            return;
        }

        getImpl(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
    }
}
