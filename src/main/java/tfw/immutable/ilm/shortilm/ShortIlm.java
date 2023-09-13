package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ShortIlm extends ImmutableLongMatrix {
    void get(final short[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
