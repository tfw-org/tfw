package tfw.immutable.ilm.charilm;

import java.io.IOException;
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
            throws IOException;

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
            throws IOException {
        boundsCheck(array.length, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
