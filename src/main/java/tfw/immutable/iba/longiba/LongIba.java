package tfw.immutable.iba.longiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iba.ImmutableBigIntegerArray;

public interface LongIba extends ImmutableBigIntegerArray {
    void get(final long[] array, final int arrayOffset, final BigInteger ibaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
