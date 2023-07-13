package tfw.immutable.ilm.stringilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface StringIlm extends ImmutableLongMatrix {
    String[] toArray() throws DataInvalidException;

    String[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    void toArray(String[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    void toArray(
            String[] array,
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
