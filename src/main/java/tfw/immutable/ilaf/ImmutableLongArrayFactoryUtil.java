package tfw.immutable.ilaf;

import tfw.check.Argument;

public final class ImmutableLongArrayFactoryUtil {
    private ImmutableLongArrayFactoryUtil() {}

    public static <T> T[] assertNotNullAndClone(T[] array, String name) {
        Argument.assertNotNull(array, name);
        for (int i = 0; i < array.length; i++) {
            Argument.assertNotNull(array[i], name + "[" + i + "]");
        }
        return array.clone();
    }
}
