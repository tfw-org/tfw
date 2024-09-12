package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedShortIla extends ImmutableLongArray {
    void get(final short[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
