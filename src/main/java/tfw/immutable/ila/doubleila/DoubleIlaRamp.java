package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaRamp {
    private DoubleIlaRamp() {
        // non-instantiable class
    }

    public static DoubleIla create(double startValue, double increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new DoubleIlaImpl(startValue, increment, length);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final double startValue;
        private final double increment;
        private final long length;

        private DoubleIlaImpl(double startValue, double increment, long length) {
            this.startValue = startValue;
            this.increment = increment;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // double value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            double value = startValue + increment * start;
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
