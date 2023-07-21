package tfw.immutable.ila.doubleila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractDoubleIla extends AbstractIla implements DoubleIla {
    protected abstract void toArrayImpl(double[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractDoubleIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final double[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    @Override
    public final void toArray(
            final double[] array, final int offset, final int stride, final long ilaStart, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, ilaStart, length);
        toArrayImpl(array, offset, stride, ilaStart, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
