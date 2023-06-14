package tfw.immutable.ilm.byteilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ByteIlm extends ImmutableLongMatrix {
    public byte[] toArray() throws DataInvalidException;

    public byte[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    public void toArray(byte[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    public void toArray(
            byte[] array,
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
