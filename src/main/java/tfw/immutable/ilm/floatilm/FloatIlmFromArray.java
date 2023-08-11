package tfw.immutable.ilm.floatilm;

import tfw.check.Argument;

public final class FloatIlmFromArray {
    private FloatIlmFromArray() {}

    public static FloatIlm create(float[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new MyFloatIlm(array, width);
    }

    private static class MyFloatIlm extends AbstractFloatIlm {
        private final float[] array;

        MyFloatIlm(float[] array, int width) {
            super(width, width == 0 ? 0 : array.length / width);

            this.array = array;
        }

        protected void toArrayImpl(
                final float[] array, int offset, long rowStart, long colStart, int rowCount, int colCount) {
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
