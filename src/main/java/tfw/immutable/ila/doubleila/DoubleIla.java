package tfw.immutable.ila.doubleila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

public interface DoubleIla extends ImmutableLongArray {
    void toArray(final double[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
