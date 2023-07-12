package tfw.immutable.ila.stringila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface StringIla extends ImmutableLongArray {
    String[] toArray() throws DataInvalidException;

    String[] toArray(long start, int length) throws DataInvalidException;

    void toArray(String[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(String[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
