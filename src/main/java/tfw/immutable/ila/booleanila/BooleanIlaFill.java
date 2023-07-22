package tfw.immutable.ila.booleanila;

import tfw.check.Argument;

public final class BooleanIlaFill {
    private BooleanIlaFill() {
        // non-instantiable class
    }

    public static BooleanIla create(boolean value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyBooleanIla(value, length);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final boolean value;

        MyBooleanIla(boolean value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
