package tfw.immutable.ilm.stringilm;

import tfw.check.Argument;

public final class StringIlmFromArray {
    private StringIlmFromArray() {}

    public static StringIlm create(String[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new MyStringIlm(array, width);
    }

    private static class MyStringIlm extends AbstractStringIlm {
        private final String[] array;

        MyStringIlm(String[] array, int width) {
            super(width, width == 0 ? 0 : array.length / width);

            this.array = array;
        }

        protected void toArrayImpl(
                String[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount) {
            int intWidth = (int) width();

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] =
                            this.array[(i + (int) rowStart) * intWidth + (j + (int) colStart)];
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
