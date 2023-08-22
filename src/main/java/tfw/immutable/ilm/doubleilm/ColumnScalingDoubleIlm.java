package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import java.math.BigInteger;
import tfw.check.Argument;

public class ColumnScalingDoubleIlm {
    private ColumnScalingDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm doubleIlm, long numerator, long denominator) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        Argument.assertNotLessThan(numerator, 1, "numerator");
        Argument.assertNotLessThan(denominator, 1, "denominator");

        if (numerator == denominator) {
            return doubleIlm;
        } else if (numerator > denominator) {
            return new MyDoubleIlm1(doubleIlm, numerator, denominator);
        } else {
            return new MyDoubleIlm2(doubleIlm, numerator, denominator);
        }
    }

    private static class MyDoubleIlm1 extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final BigInteger outputElements;
        private final BigInteger inputElements;

        public MyDoubleIlm1(DoubleIlm doubleIlm, long outputElements, long inputElements) {
            super(
                    doubleIlm.width(),
                    BigInteger.valueOf(doubleIlm.height())
                            .multiply(BigInteger.valueOf(outputElements))
                            .divide(BigInteger.valueOf(inputElements))
                            .longValue());

            this.doubleIlm = doubleIlm;
            this.outputElements = BigInteger.valueOf(outputElements);
            this.inputElements = BigInteger.valueOf(inputElements);
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            long lastRowCopied = -1;

            for (int i = 0; i < rowCount; i++) {
                long rowToCopy = BigInteger.valueOf(rowStart + i)
                        .multiply(inputElements)
                        .divide(outputElements)
                        .longValue();

                if (rowToCopy == lastRowCopied) {
                    System.arraycopy(array, offset + (colCount * (i - 1)), array, offset + (colCount * i), colCount);
                } else {
                    doubleIlm.toArray(array, offset + (colCount * i), rowToCopy, colStart, 1, colCount);
                }
            }
        }
    }

    private static class MyDoubleIlm2 extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final long numerator;
        private final long denominator;

        public MyDoubleIlm2(DoubleIlm doubleIlm, long numerator, long denominator) {
            super(
                    doubleIlm.width(),
                    BigInteger.valueOf(doubleIlm.height())
                            .multiply(BigInteger.valueOf(numerator))
                            .divide(BigInteger.valueOf(denominator))
                            .longValue());

            this.doubleIlm = doubleIlm;
            this.numerator = numerator;
            this.denominator = denominator;
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            // TODO Auto-generated method stub

        }
    }
}
