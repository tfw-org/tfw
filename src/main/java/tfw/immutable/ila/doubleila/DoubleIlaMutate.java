package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaMutateUtil;

public final class DoubleIlaMutate {
    private DoubleIlaMutate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long index, double value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new DoubleIlaImpl(ila, index, value);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long index;
        private final double value;

        private DoubleIlaImpl(DoubleIla ila, long index, double value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            IlaMutateUtil.get(
                    index,
                    start,
                    length,
                    offset,
                    (destinationOffset, sourceStart, amount) -> ila.get(array, destinationOffset, sourceStart, amount),
                    destinationOffset -> array[destinationOffset] = value);
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
