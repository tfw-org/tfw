package tfw.immutable.iba.objectiba;

import java.math.BigInteger;
import tfw.check.Argument;
import tfw.immutable.iba.AbstractIbaFromArray;

public final class ObjectIbaFromArray {
    private ObjectIbaFromArray() {
        // non-instantiable class
    }

    public static <T> ObjectIba<T> create(T[] array) {
        return new ObjectIbaImpl<>(array);
    }

    private static class ObjectIbaImpl<T> extends AbstractIbaFromArray implements ObjectIba<T> {
        private final T[] array;

        private ObjectIbaImpl(T[] array) {
            super(checkAndReturnLength(array));

            this.array = array;
        }

        @Override
        public void get(T[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }

        private static <T> BigInteger checkAndReturnLength(T[] array) {
            Argument.assertNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
