package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
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

        MyDoubleIla(double value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
