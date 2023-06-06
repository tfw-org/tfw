package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.ImmutableLongMatrix;

public abstract class AbstractDoubleIlm extends AbstractIlm implements DoubleIlm {
    protected abstract void toArrayImpl(
            double[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractDoubleIlm(long width, long height) {
        super(width, height);
    }

    public static Object getImmutableInfo(ImmutableLongMatrix ilm) {
        if (ilm instanceof ImmutableProxy) {
            return (((ImmutableProxy) ilm).getParameters());
        } else {
            return (ilm.toString());
        }
    }

    public final double[] toArray() throws DataInvalidException {
        Argument.assertNotGreaterThan(width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(0, 0, (int) height(), (int) width());
    }

    public final double[] toArray(long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        double[] result = new double[rowCount * colCount];

        toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }

    public final void toArray(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException {
        toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
    }

    public final void toArray(
            double[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long columnStart,
            int rowCount,
            int colCount)
            throws DataInvalidException {
        Argument.assertNotNull(array, "array");

        if (width == 0 || height == 0 || array.length == 0) {
            return;
        }

        boundsCheck(array.length, offset, rowStride, colStride, rowStart, columnStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStride, colStride, rowStart, columnStart, rowCount, colCount);
    }
}
