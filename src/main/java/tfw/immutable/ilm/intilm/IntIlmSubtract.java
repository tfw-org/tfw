package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;

public class IntIlmSubtract {
    private IntIlmSubtract() {}

    public static IntIlm create(IntIlm leftIlm, IntIlm rightIlm) throws IOException {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.width(), rightIlm.width(), "leftIlm.width()", "rightIlm.width()");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new IntIlmImpl(leftIlm, rightIlm);
    }

    private static class IntIlmImpl extends AbstractIntIlm {
        private final IntIlm leftIlm;
        private final IntIlm rightIlm;

        private int[] buffer = new int[0];

        private IntIlmImpl(IntIlm leftIlm, IntIlm rightIlm) {
            this.leftIlm = leftIlm;
            this.rightIlm = rightIlm;
        }

        @Override
        protected long widthImpl() throws IOException {
            return leftIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return leftIlm.height();
        }

        @Override
        protected void getImpl(int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            if (buffer.length < leftIlm.width()) {
                buffer = new int[(int) leftIlm.width()];
            }

            leftIlm.get(array, offset, rowStart, colStart, rowCount, colCount);

            for (int i = 0; i < rowCount; i++) {
                rightIlm.get(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] -= buffer[j];
                }
            }
        }
    }
}
