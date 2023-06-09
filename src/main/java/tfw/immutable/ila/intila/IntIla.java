package tfw.immutable.ila.intila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface IntIla extends ImmutableLongArray {
    void toArray(int[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(int[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
