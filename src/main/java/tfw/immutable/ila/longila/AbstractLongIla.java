package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractLongIla extends AbstractIla implements LongIla {
    protected abstract void toArrayImpl(final long[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractLongIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final long[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
