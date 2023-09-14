package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedLongIla extends ImmutableLongArray {
    void get(final long[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
