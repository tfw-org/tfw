package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class RowConcatenateDoubleIlm {
    private RowConcatenateDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm leftIlm, DoubleIlm rightIlm) throws IOException {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new DoubleIlmImpl(leftIlm, rightIlm);
    }

    private static class DoubleIlmImpl extends AbstractDoubleIlm {
        private final StridedDoubleIlm stridedLeftIlm;
        private final StridedDoubleIlm stridedRightIlm;

        private DoubleIlmImpl(DoubleIlm leftIlm, DoubleIlm rightIlm) {
            this.stridedLeftIlm = StridedDoubleIlmFromDoubleIlm.create(leftIlm, new double[0]);
            this.stridedRightIlm = StridedDoubleIlmFromDoubleIlm.create(rightIlm, new double[0]);
        }

        @Override
        protected long widthImpl() throws IOException {
            return stridedLeftIlm.width() + stridedRightIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return stridedLeftIlm.height();
        }

        @Override
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            if (colStart + colCount <= stridedLeftIlm.width()) {
                stridedLeftIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
            } else if (colStart >= stridedLeftIlm.width()) {
                stridedRightIlm.get(
                        array, offset, colCount, 1, rowStart, colStart - stridedLeftIlm.width(), rowCount, colCount);
            } else {
                int firstAmount = (int) (stridedLeftIlm.width() - colStart);

                stridedLeftIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, firstAmount);
                stridedRightIlm.get(
                        array, offset + firstAmount, colCount, 1, rowStart, 0, rowCount, colCount - firstAmount);
            }
        }
    }
}
