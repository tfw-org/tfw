package tfw.immutable.ilm.longilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractIlmCheck;

public abstract class AbstractLongIlm extends AbstractIlm implements LongIlm {
    protected abstract void getImpl(
            final long[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException;

    protected AbstractLongIlm() {}

    @Override
    public final void get(long[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException {
        Argument.assertNotNull(array, "array");

        if (width() == 0 || height() == 0 || array.length == 0) {
            return;
        }

        AbstractIlmCheck.boundsCheck(width(), height(), array.length, offset, rowStart, colStart, rowCount, colCount);
        getImpl(array, offset, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
