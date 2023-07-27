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
            super(array.length);

            this.array = array;
        }

        protected void toArrayImpl(boolean[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
