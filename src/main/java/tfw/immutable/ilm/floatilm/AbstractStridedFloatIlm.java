package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractStridedIlmCheck;

public abstract class AbstractStridedFloatIlm extends AbstractIlm implements StridedFloatIlm {
    protected abstract void getImpl(
            float[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedFloatIlm() {}

    @Override
    public final void get(
            float[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException {
        AbstractStridedIlmCheck.boundsCheck(
                width(), height(), array.length, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
        getImpl(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
