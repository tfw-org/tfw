package tfw.immutable.iba.shortiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class ShortIbaFromArray {
    private ShortIbaFromArray() {
        // non-instantiable class
    }

    public static ShortIba create(short[] array) {
        return new ShortIbaImpl(array);
    }

    private static class ShortIbaImpl extends AbstractShortIba {
        private final short[] array;
        private final BigInteger arrayLength;

        private ShortIbaImpl(short[] array) {
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
        protected void getImpl(short[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
