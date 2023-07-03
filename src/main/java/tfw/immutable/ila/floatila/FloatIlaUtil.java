package tfw.immutable.ila.floatila;

import tfw.immutable.DataInvalidException;

public final class FloatIlaUtil {
    private FloatIlaUtil() {}

    public static final float[] toArray(final FloatIla floatIla) throws DataInvalidException {
        return toArray(floatIla, 0, (int) Math.min(floatIla.length(), Integer.MAX_VALUE));
    }

    public static final float[] toArray(final FloatIla floatIla, final long start, final int length)
            throws DataInvalidException {
        float[] result = new float[length];

        floatIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
