package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractDoubleIla extends AbstractIla implements DoubleIla {
    protected abstract void toArrayImpl(final double[] array, int offset, long start, int length) throws IOException;

    protected AbstractDoubleIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final double[] array, final int offset, final long start, final int length)
            throws IOException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
