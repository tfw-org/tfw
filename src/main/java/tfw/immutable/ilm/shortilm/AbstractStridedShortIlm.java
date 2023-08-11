package tfw.immutable.ilm.shortilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedShortIlm extends AbstractStridedIlm implements StridedShortIlm {
    protected abstract void toArrayImpl(
            short[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;

    protected AbstractStridedShortIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            short[] array,
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
