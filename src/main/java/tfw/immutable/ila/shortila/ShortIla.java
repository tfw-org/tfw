package tfw.immutable.ila.shortila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface ShortIla extends ImmutableLongArray {
    void toArray(final short[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
