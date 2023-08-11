package tfw.immutable.ilm.booleanilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedBooleanIlm extends AbstractStridedIlm implements StridedBooleanIlm {
    protected abstract void toArrayImpl(
            boolean[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractStridedBooleanIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            boolean[] array,
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
