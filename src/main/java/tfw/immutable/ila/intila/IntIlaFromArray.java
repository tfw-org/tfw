package tfw.immutable.ila.intila;

import tfw.check.Argument;

public final class IntIlaFromArray {
    private IntIlaFromArray() {
        // non-instantiable class
    }

    public static IntIla create(int[] array) {
        Argument.assertNotNull(array, "array");

        return new MyIntIla(array);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final int[] array;

        MyIntIla(int[] array) {
            super(array.length);

            this.array = array;
        }

        protected void toArrayImpl(int[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
