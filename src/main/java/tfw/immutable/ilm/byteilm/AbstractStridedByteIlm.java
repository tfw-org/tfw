package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedByteIlm extends AbstractStridedIlm implements StridedByteIlm {
    protected abstract void toArrayImpl(
            byte[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedByteIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            byte[] array,
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
