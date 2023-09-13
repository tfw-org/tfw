package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedDoubleIla extends ImmutableLongArray {
    void get(final double[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
