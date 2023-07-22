package tfw.immutable.ila.floatila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface FloatIla extends ImmutableLongArray {
    void toArray(final float[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;

    void toArray(final float[] array, final int offset, final int stride, final long start, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
