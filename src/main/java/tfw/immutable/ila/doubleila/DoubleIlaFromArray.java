package tfw.immutable.ila.doubleila;

import tfw.check.Argument;

public final class DoubleIlaFromArray {
    private DoubleIlaFromArray() {
        // non-instantiable class
    }

    public static DoubleIla create(double[] array) {
        return create(array, true);
    }

    public static DoubleIla create(double[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyDoubleIla(array, cloneArray);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final double[] array;

        MyDoubleIla(double[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
