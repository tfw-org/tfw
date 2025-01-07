package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractLongIla extends AbstractIla implements LongIla {
    protected abstract void getImpl(final long[] array, int offset, long start, int length) throws IOException;

    protected AbstractLongIla() {}

    @Override
    public final void get(final long[] array, final int offset, final long start, int length) throws IOException {
        if (length == 0) {
            return;
        }

        Argument.assertNotNull(array, "array");
        boundsCheck(array.length, offset, start, length);
        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
