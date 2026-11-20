package tfw.immutable.iba.intiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class IntIbaFromArray {
    private IntIbaFromArray() {
        // non-instantiable class
    }

    public static IntIba create(int[] array) {
        return new IntIbaImpl(array);
    }

    private static class IntIbaImpl extends AbstractIbaFromArray implements IntIba {
        private final int[] array;

        private IntIbaImpl(int[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(int[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(int[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
