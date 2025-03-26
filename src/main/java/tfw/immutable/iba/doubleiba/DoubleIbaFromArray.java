package tfw.immutable.iba.doubleiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class DoubleIbaFromArray {
    private DoubleIbaFromArray() {
        // non-instantiable class
    }

    public static DoubleIba create(double[] array) {
        return new DoubleIbaImpl(array);
    }

    private static class DoubleIbaImpl extends AbstractDoubleIba {
        private final double[] array;
        private final BigInteger arrayLength;

        private DoubleIbaImpl(double[] array) {
            Argument.assertNotNull(array, "array");

            this.array = array;
            this.arrayLength = BigInteger.valueOf(array.length);
        }

        @Override
        protected void closeImpl() {
            // Nothing to do.
        }

        @Override
        protected BigInteger lengthImpl() {
            return arrayLength;
        }

        @Override
        protected void getImpl(double[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
