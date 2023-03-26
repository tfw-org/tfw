package tfw.immutable.ila.stringila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface StringIla extends ImmutableLongArray
{
    public String[] toArray()
        throws DataInvalidException;
    public String[] toArray(long start, int length)
        throws DataInvalidException;
    public void toArray(String[] array, int offset, long start, int length)
        throws DataInvalidException;
    public void toArray(String[] array, int offset, int stride,
                        long start, int length)
        throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
