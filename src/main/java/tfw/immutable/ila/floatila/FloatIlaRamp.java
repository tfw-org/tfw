package tfw.immutable.ila.floatila;

import tfw.check.Argument;

public final class FloatIlaRamp {
    private FloatIlaRamp() {
        // non-instantiable class
    }

    public static FloatIla create(float startValue, float increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyFloatIla(startValue, increment, length);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final float startValue;
        private final float increment;
        private final long length;

        MyFloatIla(float startValue, float increment, long length) {
            this.startValue = startValue;
            this.increment = increment;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(float[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // float value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            float value = startValue + increment * start;
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
