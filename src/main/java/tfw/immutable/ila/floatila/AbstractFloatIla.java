package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractFloatIla extends AbstractIla implements FloatIla {
    protected abstract void toArrayImpl(final float[] array, int offset, long start, int length) throws IOException;

    protected AbstractFloatIla() {}

    @Override
    public final void toArray(final float[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
