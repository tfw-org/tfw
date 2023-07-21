package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractByteIla extends AbstractIla implements ByteIla {
    protected abstract void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractByteIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final byte[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    @Override
    public final void toArray(
            final byte[] array, final int offset, final int stride, final long ilaStart, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, ilaStart, length);
        toArrayImpl(array, offset, stride, ilaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
