package tfw.immutable.ila.objectila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface ObjectIla<T> extends ImmutableLongArray {
    void toArray(final T[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
