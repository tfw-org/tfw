package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface CharIla extends ImmutableLongArray {
    void toArray(final char[] array, final int arrayOffset, final long ilaStart, final int length)
            throws DataInvalidException;

    void toArray(final char[] array, final int offset, final int stride, final long start, final int length)
            throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
