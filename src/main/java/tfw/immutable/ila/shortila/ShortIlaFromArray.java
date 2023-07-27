package tfw.immutable.ila.shortila;

import tfw.check.Argument;

public final class ShortIlaFromArray {
    private ShortIlaFromArray() {
        // non-instantiable class
    }

    public static ShortIla create(short[] array) {
        Argument.assertNotNull(array, "array");

        return new MyShortIla(array);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final short[] array;

        MyShortIla(short[] array) {
            super(array.length);

            this.array = array;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
