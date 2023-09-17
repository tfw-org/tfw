package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaFill {
    private DoubleIlaFill() {
        // non-instantiable class
    }

    public static DoubleIla create(double value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new DoubleIlaImpl(value, length);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final double value;
        private final long length;

        private DoubleIlaImpl(double value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
