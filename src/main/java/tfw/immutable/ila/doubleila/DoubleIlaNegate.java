package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaNegate {
    private DoubleIlaNegate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new DoubleIlaImpl(ila);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;

        private DoubleIlaImpl(DoubleIla ila) {
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
