package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedFloatIla extends ImmutableLongArray {
    void get(final float[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
