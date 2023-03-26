package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface BooleanIla extends ImmutableLongArray
{
    public boolean[] toArray()
        throws DataInvalidException;
    public boolean[] toArray(long start, int length)
        throws DataInvalidException;
    public void toArray(boolean[] array, int offset, long start, int length)
        throws DataInvalidException;
    public void toArray(boolean[] array, int offset, int stride,
                        long start, int length)
        throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
