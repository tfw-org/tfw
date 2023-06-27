package tfw.immutable.ila.stringila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaFill {
    private StringIlaFill() {
        // non-instantiable class
    }

    public static StringIla create(String value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyStringIla(value, length);
    }

    private static class MyStringIla extends AbstractStringIla {
        private final String value;

        MyStringIla(String value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(String[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
