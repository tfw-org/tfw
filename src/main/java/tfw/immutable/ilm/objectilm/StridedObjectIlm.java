package tfw.immutable.ilm.objectilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedObjectIlm<T> extends ImmutableLongMatrix {
    void toArray(
            T[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
