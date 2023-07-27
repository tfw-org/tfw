package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class IntIlaScalarAdd {
    private IntIlaScalarAdd() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, int scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila, scalar);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final int scalar;

        MyIntIla(IntIla ila, int scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(int[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
