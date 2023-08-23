package tfw.immutable.ila.longila;

import tfw.check.Argument;

public final class LongIlaFromArray {
    private LongIlaFromArray() {
        // non-instantiable class
    }

    public static LongIla create(long[] array) {
        Argument.assertNotNull(array, "array");

        return new MyLongIla(array);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final long[] array;

        MyLongIla(long[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void toArrayImpl(long[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
