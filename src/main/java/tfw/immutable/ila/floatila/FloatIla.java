package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface FloatIla extends ImmutableLongArray {
    void toArray(final float[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
