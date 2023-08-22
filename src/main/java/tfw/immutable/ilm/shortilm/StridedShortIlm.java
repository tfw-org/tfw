package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedShortIlm extends ImmutableLongMatrix {
    void toArray(
            short[] array,
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
