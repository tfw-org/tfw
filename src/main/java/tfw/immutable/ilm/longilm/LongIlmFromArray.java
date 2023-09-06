package tfw.immutable.ilm.longilm;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlmFromArray {
    private LongIlmFromArray() {}

    public static LongIlm create(long[] array, int width) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(width, 0, "width");

        if (width != 0) {
            Argument.assertEquals(array.length % width, 0, "array.length % width", "0");
        }

        return new MyLongIlm(array, width);
    }

    private static class MyLongIlm extends AbstractLongIlm {
        private final long[] array;
        private final int ilmWidth;

        MyLongIlm(long[] array, int ilmWidth) {
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
        protected void toArrayImpl(
                final long[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
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
