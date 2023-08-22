package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedByteIlm extends ImmutableLongMatrix {
    void toArray(
            byte[] array,
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
