package tfw.immutable.ila.stringila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractStringIla extends AbstractIla implements StringIla {
    protected abstract void toArrayImpl(String[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractStringIla(long length) {
        super(length);
    }

    public final String[] toArray() throws DataInvalidException {
        if (length() > (long) Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

        return toArray((long) 0, (int) length());
    }

    public final String[] toArray(long start, int length) throws DataInvalidException {
        String[] result = new String[length];

        toArray(result, 0, start, length);

        return result;
    }

    public final void toArray(String[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(String[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
