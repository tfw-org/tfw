package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractByteIla extends AbstractIla implements ByteIla {
    protected abstract void toArrayImpl(final byte[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractByteIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final byte[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
