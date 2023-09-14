package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedShortIla extends AbstractIla implements StridedShortIla {
    protected abstract void getImpl(final short[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedShortIla() {}

    @Override
    public void get(final short[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
