package tfw.immutable.iba.byteiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

public final class ByteIbaFromArray {
    private ByteIbaFromArray() {
        // non-instantiable class
    }

    public static ByteIba create(byte[] array) {
        return new ByteIbaImpl(array);
    }

    private static class ByteIbaImpl extends AbstractIbaFromArray implements ByteIba {
        private final byte[] array;

        private ByteIbaImpl(byte[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(byte[] array, int offset, BigInteger start, int length) {
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(byte[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
