package tfw.immutable.ilm.charilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedCharIlm extends AbstractStridedIlm implements StridedCharIlm {
    protected abstract void toArrayImpl(
            char[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractStridedCharIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            char[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException {
        boundsCheck(array.length, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
