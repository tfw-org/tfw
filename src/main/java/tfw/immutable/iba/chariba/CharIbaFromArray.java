package tfw.immutable.iba.chariba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class CharIbaFromArray {
    private CharIbaFromArray() {
        // non-instantiable class
    }

    public static CharIba create(char[] array) {
        return new CharIbaImpl(array);
    }

    private static class CharIbaImpl extends AbstractIbaFromArray implements CharIba {
        private final char[] array;

        private CharIbaImpl(char[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(char[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(char[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
