package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaFiltered {
    private DoubleIlaFiltered() {
        // non-instantiable class
    }

    public interface DoubleFilter {
        boolean matches(double value);
    }

    public static DoubleIla create(DoubleIla ila, DoubleFilter filter, double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new DoubleIlaImpl(ila, filter, buffer);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final DoubleFilter filter;
        private final double[] buffer;

        private DoubleIlaImpl(DoubleIla ila, DoubleFilter filter, double[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            DoubleIlaIterator oii = new DoubleIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(double[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator oii = new DoubleIlaIterator(DoubleIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                double node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
