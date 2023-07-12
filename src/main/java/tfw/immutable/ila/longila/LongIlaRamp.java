package tfw.immutable.ila.longila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaRamp {
    private LongIlaRamp() {
        // non-instantiable class
    }

    public static LongIla create(long startValue, long increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyLongIla(startValue, increment, length);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final long startValue;
        private final long increment;

        MyLongIla(long startValue, long increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // long value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            long value = startValue + increment * start;
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
