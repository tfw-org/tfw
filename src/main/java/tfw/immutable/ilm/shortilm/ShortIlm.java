package tfw.immutable.ilm.shortilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ShortIlm extends ImmutableLongMatrix {
    public short[] toArray() throws DataInvalidException;

    public short[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    public void toArray(short[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    public void toArray(
            short[] array,
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
