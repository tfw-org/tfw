package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractIlmCheck;

public abstract class AbstractBooleanIlm extends AbstractIlm implements BooleanIlm {
    protected abstract void getImpl(
            final boolean[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException;

    protected AbstractBooleanIlm() {}

    @Override
    public final void get(boolean[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
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
