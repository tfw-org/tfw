package tfw.immutable.ila.charila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaFromArray {
    private CharIlaFromArray() {
        // non-instantiable class
    }

    public static CharIla create(char[] array) {
        return create(array, true);
    }

    public static CharIla create(char[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyCharIla(array, cloneArray);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final char[] array;

        MyCharIla(char[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (char[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
