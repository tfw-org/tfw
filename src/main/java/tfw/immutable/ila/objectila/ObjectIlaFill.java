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

    public static ObjectIla create(Object value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyObjectIla(value, length);
    }

    private static class MyObjectIla extends AbstractObjectIla {
        private final Object value;

        MyObjectIla(Object value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(Object[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
