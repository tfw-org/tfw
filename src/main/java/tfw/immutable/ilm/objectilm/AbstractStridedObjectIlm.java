package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import tfw.immutable.ilm.AbstractStridedIlm;

public abstract class AbstractStridedObjectIlm<T> extends AbstractStridedIlm implements StridedObjectIlm<T> {
    protected abstract void toArrayImpl(
            T[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;

    protected AbstractStridedObjectIlm(final long width, final long height) {
        super(width, height);
    }

    @Override
    public final void toArray(
            T[] array,
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
