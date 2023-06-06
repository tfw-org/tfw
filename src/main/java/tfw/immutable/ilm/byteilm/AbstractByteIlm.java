package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractByteIlm extends AbstractIlm implements ByteIlm {
    protected abstract void toArrayImpl(
            byte[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractByteIlm(long width, long height) {
        super(width, height);
    }

    public final byte[] toArray() throws DataInvalidException {
        Argument.assertNotGreaterThan(width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(0, 0, (int) height(), (int) width());
    }

    public final byte[] toArray(long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        byte[] result = new byte[rowCount * colCount];

        toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }

    public final void toArray(byte[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException {
        toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
    }

    public final void toArray(
            byte[] array,
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
