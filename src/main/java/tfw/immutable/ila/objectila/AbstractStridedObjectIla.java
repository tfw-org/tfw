package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedObjectIla<T> extends AbstractIla implements StridedObjectIla<T> {
    protected abstract void getImpl(final T[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedObjectIla() {}

    @Override
    public void get(final T[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
