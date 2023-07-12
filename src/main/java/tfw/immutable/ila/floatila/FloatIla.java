package tfw.immutable.ila.floatila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface FloatIla extends ImmutableLongArray {
    void toArray(float[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(float[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
