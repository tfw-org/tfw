package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaScalarMultiply {
    private IntIlaScalarMultiply() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, int scalar) {
        Argument.assertNotNull(ila, "ila");

        return new IntIlaImpl(ila, scalar);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla ila;
        private final int scalar;

        private IntIlaImpl(IntIla ila, int scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
