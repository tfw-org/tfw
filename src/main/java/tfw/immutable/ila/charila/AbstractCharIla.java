package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractCharIla extends AbstractIla implements CharIla {
    protected abstract void toArrayImpl(final char[] array, int offset, long start, int length) throws IOException;

    protected AbstractCharIla() {}

    @Override
    public final void toArray(final char[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
