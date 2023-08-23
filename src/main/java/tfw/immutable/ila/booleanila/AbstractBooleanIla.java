package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractBooleanIla extends AbstractIla implements BooleanIla {
    protected abstract void toArrayImpl(final boolean[] array, int offset, long start, int length) throws IOException;

    protected AbstractBooleanIla() {}

    @Override
    public final void toArray(final boolean[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
