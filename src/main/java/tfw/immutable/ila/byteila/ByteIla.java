package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface ByteIla extends ImmutableLongArray {
    void get(final byte[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
