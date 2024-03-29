package tfw.immutable.ila.objectila;

import tfw.check.Argument;

public final class ObjectIlaFromArray {
    private ObjectIlaFromArray() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(T[] array) {
        Argument.assertNotNull(array, "array");

        return new ObjectIlaImpl<>(array);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final T[] array;

        private ObjectIlaImpl(T[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(T[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
