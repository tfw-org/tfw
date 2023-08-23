package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaScalarMultiply {
    private DoubleIlaScalarMultiply() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, double scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila, scalar);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final double scalar;

        MyDoubleIla(DoubleIla ila, double scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
