package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface BooleanIla extends ImmutableLongArray {
    void toArray(boolean[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(boolean[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
