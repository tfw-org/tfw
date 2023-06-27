package tfw.immutable.ila.intila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaFromArray {
    private IntIlaFromArray() {
        // non-instantiable class
    }

    public static IntIla create(int[] array) {
        return create(array, true);
    }

    public static IntIla create(int[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyIntIla(array, cloneArray);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final int[] array;

        MyIntIla(int[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (int[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
