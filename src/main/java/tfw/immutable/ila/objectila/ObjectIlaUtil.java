package tfw.immutable.ila.objectila;

import java.util.Arrays;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaUtil {
    private ObjectIlaUtil() {}

    public static <T> T[] toArray(final ObjectIla<T> objectIla, T[] array) throws DataInvalidException {
        return toArray(objectIla, 0, (int) Math.min(objectIla.length(), Integer.MAX_VALUE), array);
    }

    public static <T> T[] toArray(final ObjectIla<T> objectIla, final long ilaStart, final int length, final T[] array)
            throws DataInvalidException {
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
