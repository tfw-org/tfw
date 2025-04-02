package tfw.immutable.iba.chariba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

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
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(char[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
