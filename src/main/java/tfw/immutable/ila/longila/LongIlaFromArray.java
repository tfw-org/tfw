package tfw.immutable.ila.longila;

import tfw.check.Argument;

public final class LongIlaFromArray {
    private LongIlaFromArray() {
        // non-instantiable class
    }

    public static LongIla create(long[] array) {
        Argument.assertNotNull(array, "array");

        return new LongIlaImpl(array);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final long[] array;

        private LongIlaImpl(long[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
