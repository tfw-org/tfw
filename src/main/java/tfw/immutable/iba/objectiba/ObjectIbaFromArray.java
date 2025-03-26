package tfw.immutable.iba.objectiba;

import java.math.BigInteger;
import tfw.check.Argument;

public final class ObjectIbaFromArray {
    private ObjectIbaFromArray() {
        // non-instantiable class
    }

    public static <T> ObjectIba<T> create(T[] array) {
        return new ObjectIbaImpl<>(array);
    }

    private static class ObjectIbaImpl<T> extends AbstractObjectIba<T> {
        private final T[] array;
        private final BigInteger arrayLength;

        private ObjectIbaImpl(T[] array) {
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
        protected void getImpl(T[] array, int offset, BigInteger start, int length) {
            System.arraycopy(this.array, (int) start.longValue(), array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
