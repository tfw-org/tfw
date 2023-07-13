package tfw.immutable.ilm.charilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface CharIlm extends ImmutableLongMatrix {
    char[] toArray() throws DataInvalidException;

    char[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    void toArray(char[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    void toArray(
            char[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long columnStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
