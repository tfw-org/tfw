package tfw.immutable.iba;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;

public interface ImmutableBigIntegerArray extends Closeable {
    BigInteger length() throws IOException;
}
