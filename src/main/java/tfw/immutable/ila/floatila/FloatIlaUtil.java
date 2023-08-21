package tfw.immutable.ila.floatila;

import java.io.IOException;

public final class FloatIlaUtil {
    private FloatIlaUtil() {}

    public static float[] toArray(final FloatIla floatIla) throws IOException {
        return toArray(floatIla, 0, (int) Math.min(floatIla.length(), Integer.MAX_VALUE));
    }

    public static float[] toArray(final FloatIla floatIla, final long ilaStart, int length) throws IOException {
        float[] result = new float[length];

        floatIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
