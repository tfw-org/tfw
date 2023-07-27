package tfw.immutable.ila.intila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractIntIla extends AbstractIla implements IntIla {
    protected abstract void toArrayImpl(final int[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractIntIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final int[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
