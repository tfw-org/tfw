package tfw.immutable.iba.booleaniba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iba.ImmutableBigIntegerArray;

public interface BooleanIba extends ImmutableBigIntegerArray {
    void get(final boolean[] array, final int arrayOffset, final BigInteger ibaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
