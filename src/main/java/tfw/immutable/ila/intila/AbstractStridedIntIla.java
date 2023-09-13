package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedIntIla extends AbstractIla implements StridedIntIla {
    protected abstract void getImpl(final int[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedIntIla() {}

    @Override
    public void get(final int[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
