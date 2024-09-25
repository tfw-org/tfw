package tfw.immutable.ila.bitila;

import java.io.IOException;

public interface BitIla {
    long MAX_BITS_IN_ARRAY = (Integer.MAX_VALUE - 8) * (long) Long.SIZE;

    long lengthInBits() throws IOException;

    void get(final long[] array, final long arrayOffsetInBits, final long ilaStartInBits, final long lengthInBits)
            throws IOException;
}
