package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractShortIla extends AbstractIla implements ShortIla {
    protected abstract void toArrayImpl(final short[] array, int offset, long start, int length) throws IOException;

    protected AbstractShortIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final short[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
