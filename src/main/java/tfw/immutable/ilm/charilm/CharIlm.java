package tfw.immutable.ilm.charilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface CharIlm extends ImmutableLongMatrix {
    void get(final char[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
