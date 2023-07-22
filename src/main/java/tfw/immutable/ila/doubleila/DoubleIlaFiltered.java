package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

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

        return new MyDoubleIla(ila, filter, buffer);
    }

    private static class MyDoubleIla implements DoubleIla {
        private final DoubleIla ila;
        private final DoubleFilter filter;
        private final double[] buffer;

        private long length = -1;

        private MyDoubleIla(DoubleIla ila, DoubleFilter filter, double[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(double[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            DoubleIlaIterator oii = new DoubleIlaIterator(DoubleIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                double node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                DoubleIlaIterator oii = new DoubleIlaIterator(ila, buffer.clone());

                try {
                    while (oii.hasNext()) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                } catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
