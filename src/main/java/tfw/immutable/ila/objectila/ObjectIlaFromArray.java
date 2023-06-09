package tfw.immutable.ila.objectila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFromArray {
    private ObjectIlaFromArray() {
        // non-instantiable class
    }

    public static ObjectIla create(Object[] array) {
        return create(array, true);
    }

    public static ObjectIla create(Object[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyObjectIla(array, cloneArray);
    }

    private static class MyObjectIla extends AbstractObjectIla {
        private final Object[] array;

        MyObjectIla(Object[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(Object[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
