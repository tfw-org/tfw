package tfw.immutable.ilm.intilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class IntIlmSubtract {
    private IntIlmSubtract() {}

    public static IntIlm create(IntIlm leftIlm, IntIlm rightIlm) {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.width(), rightIlm.width(), "leftIlm.width()", "rightIlm.width()");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new MyIntIlm(leftIlm, rightIlm);
    }

    private static class MyIntIlm extends AbstractIntIlm {
        private final IntIlm leftIlm;
        private final IntIlm rightIlm;

        private int[] buffer = new int[0];

        public MyIntIlm(IntIlm leftIlm, IntIlm rightIlm) {
            super(leftIlm.width(), leftIlm.height());

            this.leftIlm = leftIlm;
            this.rightIlm = rightIlm;
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            if (buffer.length < leftIlm.width()) {
                buffer = new int[(int) leftIlm.width()];
            }

            leftIlm.toArray(array, offset, rowStart, colStart, rowCount, colCount);

            for (int i = 0; i < rowCount; i++) {
                rightIlm.toArray(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] -= buffer[j];
                }
            }
        }
    }
}
