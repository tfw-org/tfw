package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractStridedIlmCheck;

public abstract class AbstractStridedIntIlm extends AbstractIlm implements StridedIntIlm {
    protected abstract void toArrayImpl(
            int[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedIntIlm() {}

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
            throws IOException {
        AbstractStridedIlmCheck.boundsCheck(
                width(), height(), array.length, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
