package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface CharIla extends ImmutableLongArray {
    void toArray(final char[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
