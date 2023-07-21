package tfw.immutable.ila.objectila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFill {
    private ObjectIlaFill() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(T value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyObjectIla<>(value, length);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final T value;

        MyObjectIla(T value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(T[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
