package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlmFromArray {
    private BooleanIlmFromArray() {}

    public static BooleanIlm create(boolean[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new BooleanIlmImpl(array, width);
    }

    private static class BooleanIlmImpl extends AbstractBooleanIlm {
        private final boolean[] array;
        private final int ilmWidth;

        private BooleanIlmImpl(boolean[] array, int ilmWidth) {
            this.array = array;
            this.ilmWidth = ilmWidth;
        }

        @Override
        protected long widthImpl() {
            return ilmWidth;
        }

        @Override
        protected long heightImpl() {
            return ilmWidth == 0 ? 0 : array.length / ilmWidth;
        }

        @Override
        protected void getImpl(
                final boolean[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
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
