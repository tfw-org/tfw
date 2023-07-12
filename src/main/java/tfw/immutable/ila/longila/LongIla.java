package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface LongIla extends ImmutableLongArray {
    void toArray(long[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(long[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
