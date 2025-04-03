package tfw.immutable.iba.byteiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

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
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(byte[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
