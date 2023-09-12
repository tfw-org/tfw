package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaFill {
    private DoubleIlaFill() {
        // non-instantiable class
    }

    public static DoubleIla create(double value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyDoubleIla(value, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final double value;
        private final long length;

        MyDoubleIla(double value, long length) {
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
