package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface DoubleIla extends ImmutableLongArray {
    void toArray(final double[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
