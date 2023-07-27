package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractCharIla extends AbstractIla implements CharIla {
    protected abstract void toArrayImpl(final char[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractCharIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final char[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
