package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface ObjectIla<T> extends ImmutableLongArray {
    void get(final T[] array, final int arrayOffset, final long ilaStart, int length) throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
