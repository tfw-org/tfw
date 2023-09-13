package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedIntIla extends ImmutableLongArray {
    void get(final int[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
