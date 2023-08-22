package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface BooleanIlm extends ImmutableLongMatrix {
    void toArray(final boolean[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
