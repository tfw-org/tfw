package tfw.immutable.ila.shortila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface ShortIla extends ImmutableLongArray {
    void toArray(short[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(short[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
