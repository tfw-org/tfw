package tfw.immutable.iba.booleaniba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class BooleanIbaFromArray {
    private BooleanIbaFromArray() {
        // non-instantiable class
    }

    public static BooleanIba create(boolean[] array) {
        return new BooleanIbaImpl(array);
    }

    private static class BooleanIbaImpl extends AbstractIbaFromArray implements BooleanIba {
        private final boolean[] array;

        private BooleanIbaImpl(boolean[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(boolean[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(boolean[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
