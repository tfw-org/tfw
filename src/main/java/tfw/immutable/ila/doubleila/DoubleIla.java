package tfw.immutable.ila.doubleila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface DoubleIla extends ImmutableLongArray {
    void toArray(double[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(double[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
