package tfw.immutable.ilm.booleanilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractBooleanIlm extends AbstractIlm implements BooleanIlm {
    protected abstract void toArrayImpl(
            boolean[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException;

    protected AbstractBooleanIlm(long width, long height) {
        super(width, height);
    }

    public final void toArray(boolean[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotNull(array, "array");

        if (width == 0 || height == 0 || array.length == 0) {
            return;
        }

        boundsCheck(array.length, offset, rowStart, colStart, rowCount, colCount);
        toArrayImpl(array, offset, rowStart, colStart, rowCount, colCount);
    }
}
// AUTO GENERATED FROM TEMPLATE
