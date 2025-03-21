package tfw.immutable.iis.bitiis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements BitIis and provides
 * some common parameter validation.
 */
public abstract class AbstractBitIis implements BitIis {
    /**
     * This method replaces the actual close method.
     *
     * @throws IOException if something happens while trying to read the bits.
     */
    protected abstract void closeImpl() throws IOException;

    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the bits.
     * @param offset into the array to put the first bit.
     * @param length of bits to put into the array.
     * @return the number of bits put into the array.
     * @throws IOException if something happens while trying to read the bits.
     */
    protected abstract long readImpl(long[] array, long offset, long length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of bits to skip.
     * @return the number of bits skipped.
     * @throws IOException if something happens while trying to skip the bits.
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
    public final long read(final long[] array, final long arrayOffsetInBits, final long lengthInBits)
            throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "arrayOffsetInBits");
        Argument.assertLessThan(arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", "BitIis.MAX_BITS_IN_ARRAY");
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");
        Argument.assertLessThan(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", "BitsIis.MAX_BITS_IN_ARRAY");

        if (closed) {
            return -1;
        }
        if (lengthInBits == 0) {
            return 0;
        }

        return readImpl(array, arrayOffsetInBits, lengthInBits);
    }

    @Override
    public final long skip(final long numberOfBIts) throws IOException {
        if (closed) {
            return -1;
        }
        if (numberOfBIts < 1) {
            return 0;
        }

        return skipImpl(numberOfBIts);
    }
}
