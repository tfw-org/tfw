package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public interface ByteIla extends ImmutableLongArray {
    void toArray(byte[] array, int offset, long start, int length) throws DataInvalidException;

    void toArray(byte[] array, int offset, int stride, long start, int length) throws DataInvalidException;
}
// AUTO GENERATED FROM TEMPLATE
