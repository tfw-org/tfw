package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractIntIla extends AbstractIla implements IntIla {
    protected abstract void toArrayImpl(final int[] array, int offset, long start, int length) throws IOException;

    protected AbstractIntIla() {}

    @Override
    public final void toArray(final int[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
