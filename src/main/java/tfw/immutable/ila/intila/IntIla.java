package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface IntIla extends ImmutableLongArray {
    void toArray(final int[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
