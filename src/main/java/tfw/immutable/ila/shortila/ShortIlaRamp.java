package tfw.immutable.ila.shortila;

import tfw.check.Argument;

public final class ShortIlaRamp {
    private ShortIlaRamp() {
        // non-instantiable class
    }

    public static ShortIla create(short startValue, short increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyShortIla(startValue, increment, length);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final short startValue;
        private final short increment;

        MyShortIla(short startValue, short increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // short value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            short value = (short) (startValue + increment * start);
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
