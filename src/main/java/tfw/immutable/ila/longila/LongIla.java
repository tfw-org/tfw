package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface LongIla extends ImmutableLongArray {
    public long[] toArray() throws DataInvalidException;

    public long[] toArray(long start, int length) throws DataInvalidException;

    public void toArray(long[] array, int offset, long start, int length) throws DataInvalidException;

    public void toArray(long[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
