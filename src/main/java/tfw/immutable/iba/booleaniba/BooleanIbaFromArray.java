package tfw.immutable.iba.booleaniba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class BooleanIbaFromArray {
    private BooleanIbaFromArray() {
        // non-instantiable class
    }

    public static BooleanIba create(boolean[] array) {
        return new BooleanIbaImpl(array);
    }

    private static class BooleanIbaImpl extends AbstractBooleanIba {
        private final boolean[] array;
        private final BigInteger arrayLength;

        private BooleanIbaImpl(boolean[] array) {
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
        protected void getImpl(boolean[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
