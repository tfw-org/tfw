package tfw.immutable.ilm.doubleilm;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.ImmutableLongMatrix;

public interface DoubleIlm extends ImmutableLongMatrix {
    double[] toArray() throws DataInvalidException;

    double[] toArray(long rowStart, long columnStart, int rowCount, int colCount) throws DataInvalidException;

    void toArray(double[] array, int offset, long rowStart, long columnStart, int rowCount, int colCount)
            throws DataInvalidException;

    void toArray(
            double[] array,
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
