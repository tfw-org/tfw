package tfw.immutable.ila.charila;

import tfw.check.Argument;

public final class CharIlaFromArray {
    private CharIlaFromArray() {
        // non-instantiable class
    }

    public static CharIla create(char[] array) {
        Argument.assertNotNull(array, "array");

        return new MyCharIla(array);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final char[] array;

        MyCharIla(char[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
