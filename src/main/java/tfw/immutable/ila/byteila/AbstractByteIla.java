package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIla;

/**
 *
 * @immutables.types=all
 */
public abstract class AbstractByteIla extends AbstractIla implements ByteIla {
    protected abstract void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
            throws DataInvalidException;

    protected AbstractByteIla(long length) {
        super(length);
    }

    public final byte[] toArray() throws DataInvalidException {
        if (length() > (long) Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

        return toArray((long) 0, (int) length());
    }

    public final byte[] toArray(long start, int length) throws DataInvalidException {
        byte[] result = new byte[length];

        toArray(result, 0, start, length);

        return result;
    }

    public final void toArray(byte[] array, int offset, long start, int length) throws DataInvalidException {
        toArray(array, offset, 1, start, length);
    }

    public final void toArray(final byte[] array, int offset, int stride, long start, int length)
            throws DataInvalidException {
        if (length == 0) {
            return;
        }

        boundsCheck(array.length, offset, stride, start, length);
        toArrayImpl(array, offset, stride, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
