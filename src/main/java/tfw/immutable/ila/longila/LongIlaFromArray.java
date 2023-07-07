package tfw.immutable.ila.longila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaFromArray {
    private LongIlaFromArray() {
        // non-instantiable class
    }

    public static LongIla create(long[] array) {
        return create(array, true);
    }

    public static LongIla create(long[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyLongIla(array, cloneArray);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final long[] array;

        MyLongIla(long[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
