package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface ByteIla extends ImmutableLongArray {
    void toArray(final byte[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;

    void toArray(final byte[] array, final int offset, final int stride, final long start, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
