package tfw.immutable.ilm.floatilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractFloatIlm extends AbstractIlm implements FloatIlm {
    protected abstract void toArrayImpl(
            float[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractFloatIlm(long width, long height) {
        super(width, height);
    }

    public final void toArray(float[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException {
        toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
    }

    public final void toArray(
            float[] array,
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
// AUTO GENERATED FROM TEMPLATE
