package tfw.immutable.ila.floatila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractFloatIla extends AbstractIla implements FloatIla {
    protected abstract void toArrayImpl(final float[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractFloatIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final float[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
