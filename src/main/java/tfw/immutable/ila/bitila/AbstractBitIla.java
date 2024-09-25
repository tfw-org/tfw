package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;

public abstract class AbstractBitIla implements BitIla {
    protected abstract void getImpl(
            final long[] array, final long arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException;

    protected abstract long lengthInBitsImpl() throws IOException;

    @Override
    public final long lengthInBits() throws IOException {
        return lengthInBitsImpl();
    }

    @Override
    public final void get(
            final long[] array, final long arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(
                arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThanOrEquals(
                arrayOffsetInBits + lengthInBits, array.length * (long) Long.SIZE, "offset", "array.length");
        Argument.assertNotLessThan(ilaStartInBits, 0, "start");
        Argument.assertNotLessThan(lengthInBits, 0, "length");
        Argument.assertNotGreaterThanOrEquals(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ilaStartInBits + lengthInBits, lengthInBits(), "start+length", "Ila.lengthInBits()");

        if (lengthInBits == 0) {
            return;
        }

        getImpl(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
    }
}
