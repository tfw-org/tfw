package tfw.immutable.iba.intiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

public final class IntIbaFromArray {
    private IntIbaFromArray() {
        // non-instantiable class
    }

    public static IntIba create(int[] array) {
        return new IntIbaImpl(array);
    }

    private static class IntIbaImpl extends AbstractIbaFromArray implements IntIba {
        private final int[] array;

        private IntIbaImpl(int[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(int[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static BigInteger checkAndReturnLength(int[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
