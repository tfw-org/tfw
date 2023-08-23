package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractLongIla extends AbstractIla implements LongIla {
    protected abstract void toArrayImpl(final long[] array, int offset, long start, int length) throws IOException;

    protected AbstractLongIla() {}

    @Override
    public final void toArray(final long[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
