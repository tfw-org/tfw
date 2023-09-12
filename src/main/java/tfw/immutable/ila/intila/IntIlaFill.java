package tfw.immutable.ila.intila;

import tfw.check.Argument;

public final class IntIlaFill {
    private IntIlaFill() {
        // non-instantiable class
    }

    public static IntIla create(int value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyIntIla(value, length);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final int value;
        private final long length;

        MyIntIla(int value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
