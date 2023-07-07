package tfw.immutable.ila.stringila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaFromArray {
    private StringIlaFromArray() {
        // non-instantiable class
    }

    public static StringIla create(String[] array) {
        return create(array, true);
    }

    public static StringIla create(String[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyStringIla(array, cloneArray);
    }

    private static class MyStringIla extends AbstractStringIla {
        private final String[] array;

        MyStringIla(String[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(String[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
