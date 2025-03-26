package tfw.immutable.iba.longiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class LongIbaFromArray {
    private LongIbaFromArray() {
        // non-instantiable class
    }

    public static LongIba create(long[] array) {
        return new LongIbaImpl(array);
    }

    private static class LongIbaImpl extends AbstractLongIba {
        private final long[] array;
        private final BigInteger arrayLength;

        private LongIbaImpl(long[] array) {
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
        protected void getImpl(long[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
