package tfw.immutable.iba.objectiba;

import java.io.IOException;
import java.math.BigInteger;
import tfw.immutable.iba.ImmutableBigIntegerArray;

public interface ObjectIba<T> extends ImmutableBigIntegerArray {
    void get(final T[] array, final int arrayOffset, final BigInteger ibaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
