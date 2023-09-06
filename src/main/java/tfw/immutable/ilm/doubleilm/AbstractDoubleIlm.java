package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.AbstractIlm;
import tfw.immutable.ilm.AbstractIlmCheck;

public abstract class AbstractDoubleIlm extends AbstractIlm implements DoubleIlm {
    protected abstract void toArrayImpl(
            final double[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException;

    protected AbstractDoubleIlm() {}

    public final void toArray(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws IOException {
        Argument.assertNotNull(array, "array");

        if (width() == 0 || height() == 0 || array.length == 0) {
            return;
        }

        AbstractIlmCheck.boundsCheck(width(), height(), array.length, offset, rowStart, colStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
