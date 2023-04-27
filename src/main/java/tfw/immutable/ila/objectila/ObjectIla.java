package tfw.immutable.ila.objectila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface ObjectIla<T> extends ImmutableLongArray
{
    void toArray(T[] array, int offset, long start, int length)
        throws DataInvalidException;
    void toArray(T[] array, int offset, int stride,
                        long start, int length)
        throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
