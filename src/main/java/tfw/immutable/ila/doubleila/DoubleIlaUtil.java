package tfw.immutable.ila.doubleila;

import java.io.IOException;

public final class DoubleIlaUtil {
    private DoubleIlaUtil() {}

    public static double[] toArray(final DoubleIla doubleIla) throws IOException {
        return toArray(doubleIla, 0, (int) Math.min(doubleIla.length(), Integer.MAX_VALUE));
    }

    public static double[] toArray(final DoubleIla doubleIla, final long ilaStart, int length) throws IOException {
        double[] result = new double[length];

        doubleIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
