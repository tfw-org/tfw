package tfw.immutable.iba.doubleiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iba.ImmutableBigIntegerArray;

public interface DoubleIba extends ImmutableBigIntegerArray {
    void get(final double[] array, final int arrayOffset, final BigInteger ibaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
