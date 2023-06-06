package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class RowConcatenateDoubleIlm {
    private RowConcatenateDoubleIlm() {}

    {
    }

    public static DoubleIlm create(DoubleIlm leftIlm, DoubleIlm rightIlm) {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new MyDoubleIlm(leftIlm, rightIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm leftIlm;
        private final DoubleIlm rightIlm;

        public MyDoubleIlm(DoubleIlm leftIlm, DoubleIlm rightIlm) {
            super(leftIlm.width() + rightIlm.width(), leftIlm.height());

            this.leftIlm = leftIlm;
            this.rightIlm = rightIlm;
        }

        @Override
        protected void toArrayImpl(
                double[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            if (colStart + colCount <= leftIlm.width()) {
                leftIlm.toArray(array, offset, rowStride, colStride, rowStart, colStart, rowCount, colCount);
            } else if (colStart >= leftIlm.width()) {
                rightIlm.toArray(
                        array, offset, rowStride, colStride, rowStart, colStart - leftIlm.width(), rowCount, colCount);
            } else {
                int firstAmount = (int) (leftIlm.width() - colStart);

                leftIlm.toArray(array, offset, rowStride, colStride, rowStart, colStart, rowCount, firstAmount);
                rightIlm.toArray(
                        array,
                        offset + firstAmount,
                        rowStride,
                        colStride,
                        rowStart,
                        0,
                        rowCount,
                        colCount - firstAmount);
            }
        }
    }
}
