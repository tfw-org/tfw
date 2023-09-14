package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedLongIla extends AbstractIla implements StridedLongIla {
    protected abstract void getImpl(final long[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedLongIla() {}

    @Override
    public void get(final long[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
