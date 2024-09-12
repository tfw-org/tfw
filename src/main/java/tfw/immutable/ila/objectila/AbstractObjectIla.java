package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractObjectIla<T> extends AbstractIla implements ObjectIla<T> {
    protected abstract void getImpl(final T[] array, int offset, long start, int length) throws IOException;

    protected AbstractObjectIla() {}

    @Override
    public final void get(final T[] array, final int offset, final long start, int length) throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
