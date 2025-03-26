package tfw.immutable.iba.intiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class IntIbaFromArray {
    private IntIbaFromArray() {
        // non-instantiable class
    }

    public static IntIba create(int[] array) {
        return new IntIbaImpl(array);
    }

    private static class IntIbaImpl extends AbstractIntIba {
        private final int[] array;
        private final BigInteger arrayLength;

        private IntIbaImpl(int[] array) {
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
        protected void getImpl(int[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
