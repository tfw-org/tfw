package tfw.immutable.ila.objectila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractObjectIla<T> extends AbstractIla implements ObjectIla<T> {
    protected abstract void toArrayImpl(final T[] array, int offset, long start, int length)
            throws DataInvalidException;

    protected AbstractObjectIla(final long length) {
        super(length);
    }

    @Override
    public final void toArray(final T[] array, final int offset, final long start, final int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, start, length);
        toArrayImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
