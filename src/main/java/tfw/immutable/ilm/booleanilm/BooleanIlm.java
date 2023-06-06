package tfw.immutable.ilm.booleanilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface BooleanIlm extends ImmutableLongMatrix {
    public boolean[] toArray() throws DataInvalidException;

    public boolean[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    public void toArray(boolean[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    public void toArray(
            boolean[] array,
            int offset,
            int rowStride,
            int colStride,
            long rowStart,
            long columnStart,
            int rowCount,
            int colCount)
            throws DataInvalidException;
}
