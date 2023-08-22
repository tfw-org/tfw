package tfw.immutable.ilm.objectilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class ObjectIlmUtil {
    private ObjectIlmUtil() {}

    public static <T> T[] toArray(final ObjectIlm<T> objectIlm, T[] array) throws IOException {
        return toArray(objectIlm, 0, 0, (int) objectIlm.height(), (int) objectIlm.width(), array);
    }

    public static <T> T[] toArray(
            final ObjectIlm<T> objectIlm,
            final long rowStart,
            final long columnStart,
            final int rowCount,
            int colCount,
            T[] array)
            throws IOException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        final int intArrayLength = (int) Math.min(rowCount * colCount, Integer.MAX_VALUE);

        if (array.length < intArrayLength) {
            T[] copy = Arrays.copyOf(array, intArrayLength);
            objectIlm.toArray(copy, 0, rowStart, columnStart, rowCount, colCount);

            return copy;
        } else {
            objectIlm.toArray(array, 0, rowStart, columnStart, rowCount, colCount);
            if (array.length > intArrayLength) {
                array[intArrayLength] = null;
            }

            return array;
        }
    }
}
