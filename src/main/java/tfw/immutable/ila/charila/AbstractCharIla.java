package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractCharIla extends AbstractIla implements CharIla {
    protected abstract void toArrayImpl(char[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractCharIla(long length) {
        super(length);
    }

    public final void toArray(char[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(final char[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
