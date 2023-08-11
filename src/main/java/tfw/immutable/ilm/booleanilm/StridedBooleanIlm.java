package tfw.immutable.ilm.booleanilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedBooleanIlm extends ImmutableLongMatrix {
    void toArray(
            boolean[] array,
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
