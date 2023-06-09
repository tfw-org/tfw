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

    protected AbstractDoubleIla(long length) {
        super(length);
    }

    public final void toArray(double[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(final double[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
