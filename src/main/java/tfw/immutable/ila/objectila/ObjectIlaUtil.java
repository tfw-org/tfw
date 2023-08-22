package tfw.immutable.ila.objectila;

import java.io.IOException;
import java.util.Arrays;

public final class ObjectIlaUtil {
    private ObjectIlaUtil() {}

    public static <T> T[] toArray(final ObjectIla<T> objectIla, T[] array) throws IOException {
        return toArray(objectIla, 0, (int) Math.min(objectIla.length(), Integer.MAX_VALUE), array);
    }

    public static <T> T[] toArray(final ObjectIla<T> objectIla, final long ilaStart, final int length, final T[] array)
            throws IOException {
        final int intObjectIlaLength = (int) Math.min(objectIla.length(), Integer.MAX_VALUE);

        if (array.length < intObjectIlaLength) {
            T[] copy = Arrays.copyOf(array, length);
            objectIla.toArray(copy, 0, ilaStart, length);

            return copy;
        } else {
            objectIla.toArray(array, 0, ilaStart, length);
            if (array.length > intObjectIlaLength) {
                array[intObjectIlaLength] = null;
            }

            return array;
        }
    }
}
