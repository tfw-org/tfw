package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractFloatIla extends AbstractIla implements FloatIla {
    protected abstract void getImpl(final float[] array, int offset, long start, int length) throws IOException;

    protected AbstractFloatIla() {}

    @Override
    public final void get(final float[] array, final int offset, final long start, int length) throws IOException {
        if (length == 0) {
            return;
        }

        Argument.assertNotNull(array, "array");
        boundsCheck(array.length, offset, start, length);
        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
