package tfw.immutable.iba.floatiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

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
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(float[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
