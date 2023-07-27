package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlaReverse {
    private DoubleIlaReverse() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, final double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyDoubleIla(ila, buffer);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final double[] buffer;

        MyDoubleIla(DoubleIla ila, final double[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(double[] array, int offset, long start, int length) throws DataInvalidException {
            final StridedDoubleIla stridedDoubleIla = new StridedDoubleIla(ila, buffer.clone());

            stridedDoubleIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
