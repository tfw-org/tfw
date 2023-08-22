package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;

public class IntIlmFill {
    private IntIlmFill() {}

    public static IntIlm create(int value, long width, long height) {
        Argument.assertNotLessThan(width, 0, "width");
        Argument.assertNotLessThan(height, 0, "height");

        return new MyIntIlm(value, width, height);
    }

    private static class MyIntIlm extends AbstractIntIlm {
        private final int value;

        public MyIntIlm(int value, long width, long height) {
            super(width, height);

            this.value = value;
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = value;
                }
            }
        }
    }
}
