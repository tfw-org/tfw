package tfw.immutable.ilm.longilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface LongIlm extends ImmutableLongMatrix {
    void toArray(final long[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
