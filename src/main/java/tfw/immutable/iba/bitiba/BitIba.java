package tfw.immutable.iba.bitiba;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;

public interface BitIba extends Closeable {
    long MAX_BITS_IN_ARRAY = (Integer.MAX_VALUE - 8) * (long) Long.SIZE;

    BigInteger lengthInBits() throws IOException;

    void get(final long[] array, final long arrayOffsetInBits, final BigInteger ilaStartInBits, long lengthInBits)
            throws IOException;
}
