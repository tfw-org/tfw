package tfw.immutable.iba.longiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

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
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(long[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
