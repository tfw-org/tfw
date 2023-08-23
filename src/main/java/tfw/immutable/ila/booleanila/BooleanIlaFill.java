package tfw.immutable.ila.booleanila;

import tfw.check.Argument;

public final class BooleanIlaFill {
    private BooleanIlaFill() {
        // non-instantiable class
    }

    public static BooleanIla create(boolean value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyBooleanIla(value, length);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final boolean value;
        private final long length;

        MyBooleanIla(boolean value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(boolean[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
