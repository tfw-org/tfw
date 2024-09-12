package tfw.immutable.ila.objectila;

import tfw.check.Argument;

public final class ObjectIlaFill {
    private ObjectIlaFill() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(T value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new ObjectIlaImpl<>(value, length);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final T value;
        private final long length;

        private ObjectIlaImpl(T value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(T[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
