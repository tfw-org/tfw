package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface IntIlm extends ImmutableLongMatrix {
    void toArray(final int[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
