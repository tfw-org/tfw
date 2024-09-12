package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedByteIla extends ImmutableLongArray {
    void get(final byte[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
