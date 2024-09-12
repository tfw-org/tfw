package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedBooleanIla extends ImmutableLongArray {
    void get(final boolean[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
