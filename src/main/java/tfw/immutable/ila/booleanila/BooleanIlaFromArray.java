package tfw.immutable.ila.booleanila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaFromArray {
    private BooleanIlaFromArray() {
        // non-instantiable class
    }

    public static BooleanIla create(boolean[] array) {
        return create(array, true);
    }

    public static BooleanIla create(boolean[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyBooleanIla(array, cloneArray);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final boolean[] array;

        MyBooleanIla(boolean[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (boolean[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
