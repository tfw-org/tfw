package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractLongIla extends AbstractIla implements LongIla {
    protected abstract void toArrayImpl(long[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractLongIla(long length) {
        super(length);
    }

    public final void toArray(long[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(final long[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
