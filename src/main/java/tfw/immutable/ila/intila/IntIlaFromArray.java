package tfw.immutable.ila.intila;

import tfw.check.Argument;

public final class IntIlaFromArray {
    private IntIlaFromArray() {
        // non-instantiable class
    }

    public static IntIla create(int[] array) {
        Argument.assertNotNull(array, "array");

        return new IntIlaImpl(array);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final int[] array;

        private IntIlaImpl(int[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
