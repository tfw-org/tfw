package tfw.immutable.ilm.objectilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.AbstractIlm;

public abstract class AbstractObjectIlm<T> extends AbstractIlm implements ObjectIlm<T> {
    protected abstract void toArrayImpl(
            final T[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
            throws DataInvalidException;

    protected AbstractObjectIlm(long width, long height) {
        super(width, height);
    }

    public final void toArray(T[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
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
