package tfw.immutable.ilm.objectilm;

import tfw.check.Argument;

public final class ObjectIlmFromArray {
    private ObjectIlmFromArray() {}

    public static <T> ObjectIlm<T> create(T[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new MyObjectIlm<>(array, width);
    }

    private static class MyObjectIlm<T> extends AbstractObjectIlm<T> {
        private final T[] array;

        MyObjectIlm(T[] array, int width) {
            super(width, width == 0 ? 0 : array.length / width);

            this.array = array;
        }

        protected void toArrayImpl(
                final T[] array, int offset, long rowStart, long colStart, int rowCount, int colCount) {
            int intWidth = (int) width();

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] =
                            this.array[(i + (int) rowStart) * intWidth + j + (int) colStart];
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
