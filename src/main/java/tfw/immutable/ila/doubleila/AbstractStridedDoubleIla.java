package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedDoubleIla extends AbstractIla implements StridedDoubleIla {
    protected abstract void getImpl(final double[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedDoubleIla() {}

    @Override
    public void get(final double[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
