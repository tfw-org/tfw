package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedCharIla extends AbstractIla implements StridedCharIla {
    protected abstract void getImpl(final char[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedCharIla() {}

    @Override
    public void get(final char[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
