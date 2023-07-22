package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface BooleanIla extends ImmutableLongArray {
    void toArray(final boolean[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;

    void toArray(final boolean[] array, final int offset, final int stride, final long start, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
