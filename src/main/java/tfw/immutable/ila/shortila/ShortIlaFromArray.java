package tfw.immutable.ila.shortila;

import tfw.check.Argument;

public final class ShortIlaFromArray {
    private ShortIlaFromArray() {
        // non-instantiable class
    }

    public static ShortIla create(short[] array) {
        Argument.assertNotNull(array, "array");

        return new ShortIlaImpl(array);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final short[] array;

        private ShortIlaImpl(short[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
