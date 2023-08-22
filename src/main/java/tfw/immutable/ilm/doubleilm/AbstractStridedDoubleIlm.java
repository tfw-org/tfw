package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedDoubleIlm extends AbstractStridedIlm implements StridedDoubleIlm {
    protected abstract void toArrayImpl(
            double[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedDoubleIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            double[] array,
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
