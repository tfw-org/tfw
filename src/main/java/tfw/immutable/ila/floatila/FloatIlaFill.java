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
        private final long length;

        MyFloatIla(float value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
