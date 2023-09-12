package tfw.immutable.ila.shortila;

import tfw.check.Argument;

public final class ShortIlaFill {
    private ShortIlaFill() {
        // non-instantiable class
    }

    public static ShortIla create(short value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyShortIla(value, length);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final short value;
        private final long length;

        MyShortIla(short value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
