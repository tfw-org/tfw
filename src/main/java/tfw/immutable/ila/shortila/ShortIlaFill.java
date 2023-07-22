package tfw.immutable.ila.shortila;

import tfw.check.Argument;

public final class ShortIlaFill {
    private ShortIlaFill() {
        // non-instantiable class
    }

    public static ShortIla create(short value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyShortIla(value, length);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final short value;

        MyShortIla(short value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
