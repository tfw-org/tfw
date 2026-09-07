package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractDoubleIla extends AbstractIla implements DoubleIla {
    protected abstract void getImpl(final double[] array, int offset, long start, int length) throws IOException;

    protected AbstractDoubleIla() {}

    @Override
    public final void get(final double[] array, final int offset, final long start, int length) throws IOException {
        checkClosed();

        Argument.assertNotNull(array, "array");
        boundsCheck(array.length, offset, start, length);

        if (length == 0) {
            return;
        }

        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
