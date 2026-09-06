package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.check.ClosedManager;

public abstract class AbstractBitIla implements BitIla {
    protected abstract void closeImpl() throws IOException;

    protected abstract void getImpl(
            final long[] array, final long arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException;

    protected abstract long lengthInBitsImpl() throws IOException;

    private final ClosedManager closedManager = new ClosedManager();

    @Override
    public final long lengthInBits() throws IOException {
        checkClosed();

        return lengthInBitsImpl();
    }

    @Override
    public final void get(
            final long[] array, final long arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException {
        checkClosed();

        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "offset");
        Argument.assertLessThan(arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertLessThan(
                arrayOffsetInBits + lengthInBits, array.length * (long) Long.SIZE, "offset", "array.length");
        Argument.assertNotLessThan(ilaStartInBits, 0, "start");
        Argument.assertNotLessThan(lengthInBits, 0, "length");
        Argument.assertLessThan(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ilaStartInBits + lengthInBits, lengthInBits(), "start+length", "Ila.lengthInBits()");

        if (lengthInBits == 0) {
            return;
        }

        getImpl(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
    }

    @Override
    public final void close() throws IOException {
        if (closedManager.close()) {
            closeImpl();
        }
    }

    protected void checkClosed() {
        closedManager.checkClosed();
    }
}
