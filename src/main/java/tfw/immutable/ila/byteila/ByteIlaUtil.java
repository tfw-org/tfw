package tfw.immutable.ila.byteila;

import java.io.IOException;

public final class ByteIlaUtil {
    private ByteIlaUtil() {}

    public static byte[] toArray(final ByteIla byteIla) throws IOException {
        return toArray(byteIla, 0, (int) Math.min(byteIla.length(), Integer.MAX_VALUE));
    }

    public static byte[] toArray(final ByteIla byteIla, final long ilaStart, int length) throws IOException {
        byte[] result = new byte[length];

        byteIla.get(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
