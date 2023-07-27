package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaFromArray {
    private DoubleIlaFromArray() {
        // non-instantiable class
    }

    public static DoubleIla create(double[] array) {
        Argument.assertNotNull(array, "array");

        return new MyDoubleIla(array);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final double[] array;

        MyDoubleIla(double[] array) {
            super(array.length);

            this.array = array;
        }

        protected void toArrayImpl(double[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
