package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface BitIla extends ImmutableLongArray {
    void get(final long[] array, final int arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException;
}
