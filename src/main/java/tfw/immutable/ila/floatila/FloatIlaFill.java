package tfw.immutable.ila.floatila;

import tfw.check.Argument;

public final class FloatIlaFill {
    private FloatIlaFill() {
        // non-instantiable class
    }

    public static FloatIla create(float value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyFloatIla(value, length);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final float value;

        MyFloatIla(float value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
