package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface FloatIlm extends ImmutableLongMatrix {
    void toArray(final float[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
