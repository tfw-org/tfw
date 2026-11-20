package tfw.immutable.iba.objectiba;

import java.math.BigInteger;
import tfw.check.Arguments;
import tfw.immutable.iba.AbstractIbaFromArray;
import tfw.immutable.iba.ImmutableBigIntegerArrayUtil;

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
            checkClosed();

            Arguments.checkNotNull(array, "array");
            ImmutableBigIntegerArrayUtil.validateGetParameters(array.length, offset, start, arrayLength, length);

            if (length == 0) {
                return;
            }

            System.arraycopy(this.array, start.intValueExact(), array, offset, length);
        }

        private static <T> BigInteger checkAndReturnLength(T[] array) {
            Arguments.checkNotNull(array, "array");

            return BigInteger.valueOf(array.length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
