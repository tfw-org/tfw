package tfw.immutable.ila.floatila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaFromArray {
    private FloatIlaFromArray() {
        // non-instantiable class
    }

    public static FloatIla create(float[] array) {
        return create(array, true);
    }

    public static FloatIla create(float[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyFloatIla(array, cloneArray);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final float[] array;

        MyFloatIla(float[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
