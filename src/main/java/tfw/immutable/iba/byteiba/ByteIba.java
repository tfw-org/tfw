package tfw.immutable.iba.byteiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iba.ImmutableBigIntegerArray;

public interface ByteIba extends ImmutableBigIntegerArray {
    void get(final byte[] array, final int arrayOffset, final BigInteger ibaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
