package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;
import tfw.immutable.ila.AbstractStridedIlaCheck;

public abstract class AbstractStridedByteIla extends AbstractIla implements StridedByteIla {
    protected abstract void getImpl(final byte[] array, final int offset, final int stride, long start, int length)
            throws IOException;

    protected AbstractStridedByteIla() {}

    @Override
    public void get(final byte[] array, final int offset, final int stride, final long start, int length)
            throws IOException {
        AbstractStridedIlaCheck.boundsCheck(length(), array.length, offset, stride, start, length);
        getImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
