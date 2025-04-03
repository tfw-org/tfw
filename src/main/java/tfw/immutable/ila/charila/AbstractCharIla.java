package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractCharIla extends AbstractIla implements CharIla {
    protected abstract void getImpl(final char[] array, int offset, long start, int length) throws IOException;

    protected AbstractCharIla() {}

    @Override
    public final void get(final char[] array, final int offset, final long start, int length) throws IOException {
        if (length == 0) {
            return;
        }

        Argument.assertNotNull(array, "array");
        boundsCheck(array.length, offset, start, length);
        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
