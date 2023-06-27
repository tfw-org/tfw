package tfw.immutable.ila.charila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaFill {
    private CharIlaFill() {
        // non-instantiable class
    }

    public static CharIla create(char value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(value, length);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final char value;

        MyCharIla(char value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
