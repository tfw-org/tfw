package tfw.immutable.ilm.intilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedIntIlm extends AbstractStridedIlm implements StridedIntIlm {
    protected abstract void toArrayImpl(
            int[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractStridedIntIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            int[] array,
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
