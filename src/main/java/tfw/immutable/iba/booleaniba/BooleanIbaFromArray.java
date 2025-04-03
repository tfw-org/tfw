package tfw.immutable.iba.booleaniba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

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
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(boolean[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
