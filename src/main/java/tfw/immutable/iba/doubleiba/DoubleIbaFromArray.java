package tfw.immutable.iba.doubleiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

public final class DoubleIbaFromArray {
    private DoubleIbaFromArray() {
        // non-instantiable class
    }

    public static DoubleIba create(double[] array) {
        return new DoubleIbaImpl(array);
    }

    private static class DoubleIbaImpl extends AbstractIbaFromArray implements DoubleIba {
        private final double[] array;

        private DoubleIbaImpl(double[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(double[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(double[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
