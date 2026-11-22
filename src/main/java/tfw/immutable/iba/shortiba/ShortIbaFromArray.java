package tfw.immutable.iba.shortiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class ShortIbaFromArray {
    private ShortIbaFromArray() {
        // non-instantiable class
    }

    public static ShortIba create(short[] array) {
        return new ShortIbaImpl(array);
    }

    private static class ShortIbaImpl extends AbstractIbaFromArray implements ShortIba {
        private final short[] array;

        private ShortIbaImpl(short[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(short[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(short[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
