package tfw.immutable.ilm.objectilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ObjectIlm<T> extends ImmutableLongMatrix {
    void toArray(T[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
