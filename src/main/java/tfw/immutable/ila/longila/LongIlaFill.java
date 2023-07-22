package tfw.immutable.ila.longila;

import tfw.check.Argument;

public final class LongIlaFill {
    private LongIlaFill() {
        // non-instantiable class
    }

    public static LongIla create(long value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyLongIla(value, length);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final long value;

        MyLongIla(long value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
