package tfw.immutable.ila.longila;

import java.io.IOException;

public final class LongIlaUtil {
    private LongIlaUtil() {}

    public static long[] toArray(final LongIla longIla) throws IOException {
        return toArray(longIla, 0, (int) Math.min(longIla.length(), Integer.MAX_VALUE));
    }

    public static long[] toArray(final LongIla longIla, final long ilaStart, int length) throws IOException {
        long[] result = new long[length];

        longIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
