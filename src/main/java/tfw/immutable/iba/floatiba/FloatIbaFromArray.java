package tfw.immutable.iba.floatiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class FloatIbaFromArray {
    private FloatIbaFromArray() {
        // non-instantiable class
    }

    public static FloatIba create(float[] array) {
        return new FloatIbaImpl(array);
    }

    private static class FloatIbaImpl extends AbstractIbaFromArray implements FloatIba {
        private final float[] array;

        private FloatIbaImpl(float[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(float[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(float[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
