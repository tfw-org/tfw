package tfw.immutable.ila.booleanila;

import tfw.check.Argument;

public final class BooleanIlaFromArray {
    private BooleanIlaFromArray() {
        // non-instantiable class
    }

    public static BooleanIla create(boolean[] array) {
        Argument.assertNotNull(array, "array");

        return new MyBooleanIla(array);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final boolean[] array;

        MyBooleanIla(boolean[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
