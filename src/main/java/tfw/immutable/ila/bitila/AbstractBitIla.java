package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractBitIla extends AbstractIla implements BitIla {
    protected abstract void getImpl(
            final long[] array, final int arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException;

    @Override
    public final void get(
            final long[] array, final int arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(
                arrayOffsetInBits + lengthInBits, array.length * (long) Long.SIZE, "offset", "array.length");
        Argument.assertNotLessThan(ilaStartInBits, 0, "start");
        Argument.assertNotLessThan(lengthInBits, 0, "length");
        Argument.assertNotGreaterThan(ilaStartInBits + lengthInBits, length(), "start+length", "Ila.length()");

        if (lengthInBits == 0) {
            return;
        }

        getImpl(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
    }
}
