package tfw.immutable.ilm.charilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface CharIlm extends ImmutableLongMatrix {
    void toArray(char[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
