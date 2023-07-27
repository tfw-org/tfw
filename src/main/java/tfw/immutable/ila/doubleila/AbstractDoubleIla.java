package tfw.immutable.ila.doubleila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractDoubleIla extends AbstractIla implements DoubleIla {
    protected abstract void toArrayImpl(final double[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractDoubleIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final double[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
