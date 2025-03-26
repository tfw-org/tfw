package tfw.immutable.iba.byteiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class ByteIbaFromArray {
    private ByteIbaFromArray() {
        // non-instantiable class
    }

    public static ByteIba create(byte[] array) {
        return new ByteIbaImpl(array);
    }

    private static class ByteIbaImpl extends AbstractByteIba {
        private final byte[] array;
        private final BigInteger arrayLength;

        private ByteIbaImpl(byte[] array) {
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
        protected void getImpl(byte[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
