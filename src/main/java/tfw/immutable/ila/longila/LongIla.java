package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface LongIla extends ImmutableLongArray {
    void get(final long[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
