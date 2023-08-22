package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface ShortIla extends ImmutableLongArray {
    void toArray(final short[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
