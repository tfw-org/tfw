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
        private final long length;

        MyShortIla(short startValue, short increment, long length) {
            this.startValue = startValue;
            this.increment = increment;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(short[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // short value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            short value = (short) (startValue + increment * start);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
