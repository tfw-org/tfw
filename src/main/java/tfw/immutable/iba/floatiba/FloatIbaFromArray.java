package tfw.immutable.iba.floatiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class FloatIbaFromArray {
    private FloatIbaFromArray() {
        // non-instantiable class
    }

    public static FloatIba create(float[] array) {
        return new FloatIbaImpl(array);
    }

    private static class FloatIbaImpl extends AbstractFloatIba {
        private final float[] array;
        private final BigInteger arrayLength;

        private FloatIbaImpl(float[] array) {
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
        protected void getImpl(float[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
