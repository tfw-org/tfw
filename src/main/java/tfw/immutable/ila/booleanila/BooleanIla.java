package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface BooleanIla extends ImmutableLongArray {
    void toArray(final boolean[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
