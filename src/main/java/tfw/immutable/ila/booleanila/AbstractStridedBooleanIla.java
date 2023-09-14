package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedBooleanIla extends AbstractIla implements StridedBooleanIla {
    protected abstract void getImpl(final boolean[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedBooleanIla() {}

    @Override
    public void get(final boolean[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
