package tfw.immutable.iba.shortiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

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
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(short[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
