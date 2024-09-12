package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ObjectIlm<T> extends ImmutableLongMatrix {
    void get(final T[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
