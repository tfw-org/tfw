package tfw.immutable.ila.charila;

import tfw.check.Argument;

public final class CharIlaFromArray {
    private CharIlaFromArray() {
        // non-instantiable class
    }

    public static CharIla create(char[] array) {
        Argument.assertNotNull(array, "array");

        return new CharIlaImpl(array);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final char[] array;

        private CharIlaImpl(char[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
