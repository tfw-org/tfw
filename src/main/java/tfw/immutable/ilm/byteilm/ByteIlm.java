package tfw.immutable.ilm.byteilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface ByteIlm extends ImmutableLongMatrix {
    void toArray(byte[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
