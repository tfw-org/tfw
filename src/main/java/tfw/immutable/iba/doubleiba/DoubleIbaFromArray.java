package tfw.immutable.iba.doubleiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

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
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(double[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
