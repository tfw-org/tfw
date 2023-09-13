package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedFloatIlm extends ImmutableLongMatrix {
    void get(
            float[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long colStart,
            int rowCount,
            int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
