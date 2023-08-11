package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class RowConcatenateDoubleIlm {
    private RowConcatenateDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm leftIlm, DoubleIlm rightIlm) {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new MyDoubleIlm(leftIlm, rightIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final StridedDoubleIlm stridedLeftIlm;
        private final StridedDoubleIlm stridedRightIlm;

        public MyDoubleIlm(DoubleIlm leftIlm, DoubleIlm rightIlm) {
            super(leftIlm.width() + rightIlm.width(), leftIlm.height());

            this.stridedLeftIlm = StridedDoubleIlmFromDoubleIlm.create(leftIlm, new double[0]);
            this.stridedRightIlm = StridedDoubleIlmFromDoubleIlm.create(rightIlm, new double[0]);
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            if (colStart + colCount <= stridedLeftIlm.width()) {
                stridedLeftIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
            } else if (colStart >= stridedLeftIlm.width()) {
                stridedRightIlm.toArray(
                        array, offset, colCount, 1, rowStart, colStart - stridedLeftIlm.width(), rowCount, colCount);
            } else {
                int firstAmount = (int) (stridedLeftIlm.width() - colStart);

                stridedLeftIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, firstAmount);
                stridedRightIlm.toArray(
                        array, offset + firstAmount, colCount, 1, rowStart, 0, rowCount, colCount - firstAmount);
            }
        }
    }
}
