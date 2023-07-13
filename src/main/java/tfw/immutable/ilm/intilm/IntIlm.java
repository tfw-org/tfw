package tfw.immutable.ilm.intilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface IntIlm extends ImmutableLongMatrix {
    int[] toArray() throws DataInvalidException;

    int[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    void toArray(int[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    void toArray(
            int[] array,
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
