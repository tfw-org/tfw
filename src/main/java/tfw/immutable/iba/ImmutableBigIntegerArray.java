package tfw.immutable.iba;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;

public interface ImmutableBigIntegerArray extends Closeable {
    long MAX_BITS_IN_ARRAY = (Integer.MAX_VALUE - 8) * (long) Long.SIZE;

    BigInteger length() throws IOException;
}
