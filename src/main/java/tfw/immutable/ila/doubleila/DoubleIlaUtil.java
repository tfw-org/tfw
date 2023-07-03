package tfw.immutable.ila.doubleila;

import tfw.immutable.DataInvalidException;

public final class DoubleIlaUtil {
    private DoubleIlaUtil() {}

    public static final double[] toArray(final DoubleIla doubleIla) throws DataInvalidException {
        return toArray(doubleIla, 0, (int) Math.min(doubleIla.length(), Integer.MAX_VALUE));
    }

    public static final double[] toArray(final DoubleIla doubleIla, final long start, final int length)
            throws DataInvalidException {
        double[] result = new double[length];

        doubleIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
