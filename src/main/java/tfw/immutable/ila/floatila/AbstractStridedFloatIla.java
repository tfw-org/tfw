package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedFloatIla extends AbstractIla implements StridedFloatIla {
    protected abstract void getImpl(final float[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedFloatIla() {}

    @Override
    public void get(final float[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
