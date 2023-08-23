package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
