package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StridedDoubleIlm extends ImmutableLongMatrix {
    void toArray(
            double[] array,
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
