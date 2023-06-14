package tfw.immutable.ilm.booleanilm;

import tfw.check.Argument;

public final class BooleanIlmFromArray {
    private BooleanIlmFromArray() {}

    public static BooleanIlm create(boolean[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new MyBooleanIlm(array, width);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm {
        private final boolean[] array;

        MyBooleanIlm(boolean[] array, int width) {
            super(width, width == 0 ? 0 : array.length / width);

            this.array = array;
        }

        protected void toArrayImpl(
                boolean[] array,
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
