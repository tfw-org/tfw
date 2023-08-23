package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractByteIla extends AbstractIla implements ByteIla {
    protected abstract void toArrayImpl(final byte[] array, int offset, long start, int length) throws IOException;

    protected AbstractByteIla() {}

    @Override
    public final void toArray(final byte[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
