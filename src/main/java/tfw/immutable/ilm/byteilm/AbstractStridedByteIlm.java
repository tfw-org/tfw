package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractStridedIlmCheck;

public abstract class AbstractStridedByteIlm extends AbstractIlm implements StridedByteIlm {
    protected abstract void getImpl(
            byte[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedByteIlm() {}

    @Override
    public final void get(
            byte[] array,
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
