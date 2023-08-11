package tfw.immutable.ilm.floatilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedFloatIlm extends ImmutableLongMatrix {
    void toArray(
            float[] array,
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
