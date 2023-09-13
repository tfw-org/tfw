package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractIlmCheck;

public abstract class AbstractFloatIlm extends AbstractIlm implements FloatIlm {
    protected abstract void getImpl(
            final float[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException;

    protected AbstractFloatIlm() {}

    public final void get(float[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
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
