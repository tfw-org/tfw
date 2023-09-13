package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class MultiplyDoubleIlm {
    private MultiplyDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm leftIlm, DoubleIlm rightIlm) throws IOException {
        Argument.assertNotNull(leftIlm, "leftIlm");
        Argument.assertNotNull(rightIlm, "rightIlm");
        Argument.assertEquals(leftIlm.width(), rightIlm.width(), "leftIlm.width()", "rightIlm.width()");
        Argument.assertEquals(leftIlm.height(), rightIlm.height(), "leftIlm.height()", "rightIlm.height()");

        return new MyDoubleIlm(leftIlm, rightIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm leftIlm;
        private final DoubleIlm rightIlm;

        private double[] buffer = new double[0];

        public MyDoubleIlm(DoubleIlm leftIlm, DoubleIlm rightIlm) {
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
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            if (buffer.length < colCount) {
                buffer = new double[colCount];
            }

            leftIlm.get(array, offset, rowStart, colStart, rowCount, colCount);

            for (int i = 0; i < rowCount; i++) {
                rightIlm.get(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] *= buffer[j];
                }
            }
        }
    }
}
