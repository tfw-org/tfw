package tfw.immutable.ila.byteila;

import tfw.immutable.DataInvalidException;

public final class ByteIlaUtil {
    private ByteIlaUtil() {}

    public static final byte[] toArray(final ByteIla byteIla) throws DataInvalidException {
        return toArray(byteIla, 0, (int) Math.min(byteIla.length(), Integer.MAX_VALUE));
    }

    public static final byte[] toArray(final ByteIla byteIla, final long start, final int length)
            throws DataInvalidException {
        byte[] result = new byte[length];

        byteIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
