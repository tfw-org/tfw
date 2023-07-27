package tfw.immutable.ila.floatila;

import tfw.check.Argument;

public final class FloatIlaFromArray {
    private FloatIlaFromArray() {
        // non-instantiable class
    }

    public static FloatIla create(float[] array) {
        Argument.assertNotNull(array, "array");

        return new MyFloatIla(array);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final float[] array;

        MyFloatIla(float[] array) {
            super(array.length);

            this.array = array;
        }

        protected void toArrayImpl(float[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
