package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface CharIla extends ImmutableLongArray {
    void toArray(char[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(char[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
