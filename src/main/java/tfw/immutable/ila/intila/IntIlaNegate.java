package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class IntIlaNegate {
    private IntIlaNegate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;

        MyIntIla(IntIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(int[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
