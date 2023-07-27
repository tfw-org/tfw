package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface LongIla extends ImmutableLongArray {
    void toArray(final long[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
