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
        private final long length;

        MyLongIla(long value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
