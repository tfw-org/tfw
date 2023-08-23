package tfw.immutable.ila.charila;

import tfw.check.Argument;

public final class CharIlaRamp {
    private CharIlaRamp() {
        // non-instantiable class
    }

    public static CharIla create(char startValue, char increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(startValue, increment, length);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final char startValue;
        private final char increment;
        private final long length;

        MyCharIla(char startValue, char increment, long length) {
            this.startValue = startValue;
            this.increment = increment;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // char value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            char value = (char) (startValue + increment * start);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
