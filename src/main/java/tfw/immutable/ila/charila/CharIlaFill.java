package tfw.immutable.ila.charila;

import tfw.check.Argument;

public final class CharIlaFill {
    private CharIlaFill() {
        // non-instantiable class
    }

    public static CharIla create(char value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(value, length);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final char value;
        private final long length;

        MyCharIla(char value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
