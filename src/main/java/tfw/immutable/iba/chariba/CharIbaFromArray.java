package tfw.immutable.iba.chariba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class CharIbaFromArray {
    private CharIbaFromArray() {
        // non-instantiable class
    }

    public static CharIba create(char[] array) {
        return new CharIbaImpl(array);
    }

    private static class CharIbaImpl extends AbstractCharIba {
        private final char[] array;
        private final BigInteger arrayLength;

        private CharIbaImpl(char[] array) {
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
        protected void getImpl(char[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
