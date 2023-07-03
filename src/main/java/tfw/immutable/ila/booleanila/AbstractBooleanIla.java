package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractBooleanIla extends AbstractIla implements BooleanIla {
    protected abstract void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractBooleanIla(long length) {
        super(length);
    }

    public final void toArray(boolean[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(final boolean[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
