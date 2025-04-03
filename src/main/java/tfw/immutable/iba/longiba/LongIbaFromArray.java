package tfw.immutable.iba.longiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

public final class LongIbaFromArray {
    private LongIbaFromArray() {
        // non-instantiable class
    }

    public static LongIba create(long[] array) {
        return new LongIbaImpl(array);
    }

    private static class LongIbaImpl extends AbstractIbaFromArray implements LongIba {
        private final long[] array;

        private LongIbaImpl(long[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(long[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(long[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
