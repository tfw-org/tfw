package tfw.immutable.ilm.intilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedIntIlm extends ImmutableLongMatrix {
    void toArray(
            int[] array,
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
