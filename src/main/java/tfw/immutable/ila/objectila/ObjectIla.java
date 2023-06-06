package tfw.immutable.ila.objectila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface ObjectIla extends ImmutableLongArray {
    public Object[] toArray() throws DataInvalidException;

    public Object[] toArray(long start, int length) throws DataInvalidException;

    public void toArray(Object[] array, int offset, long start, int length) throws DataInvalidException;

    public void toArray(Object[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
