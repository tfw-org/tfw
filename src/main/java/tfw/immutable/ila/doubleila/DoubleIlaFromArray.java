package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaFromArray {
    private DoubleIlaFromArray() {
        // non-instantiable class
    }

    public static DoubleIla create(double[] array) {
        Argument.assertNotNull(array, "array");

        return new DoubleIlaImpl(array);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final double[] array;

        private DoubleIlaImpl(double[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
