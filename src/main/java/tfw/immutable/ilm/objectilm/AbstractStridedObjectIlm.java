package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractStridedIlmCheck;

public abstract class AbstractStridedObjectIlm<T> extends AbstractIlm implements StridedObjectIlm<T> {
    protected abstract void getImpl(
            T[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedObjectIlm() {}

    @Override
    public final void get(
            T[] array,
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
