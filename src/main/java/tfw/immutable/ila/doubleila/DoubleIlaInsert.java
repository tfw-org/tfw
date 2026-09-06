package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaInsert {
    private DoubleIlaInsert() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long index, double value) {
        return new DoubleIlaImpl(ila, index, value);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long index;
        private final double value;

        private DoubleIlaImpl(DoubleIla ila, long index, double value) {
            Argument.assertNotNull(ila, "ila");
            Argument.assertNotLessThan(index, 0, "index");
            try {
                Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length()!", e);
            }

            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() + 1;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            IlaInsertUtil.get(
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
