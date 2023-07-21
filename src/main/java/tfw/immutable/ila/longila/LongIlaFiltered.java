package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaFiltered {
    private LongIlaFiltered() {
        // non-instantiable class
    }

    public interface LongFilter {
        boolean matches(long value);
    }

    public static LongIla create(LongIla ila, LongFilter filter, long[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyLongIla(ila, filter, buffer);
    }

    private static class MyLongIla implements LongIla {
        private final LongIla ila;
        private final LongFilter filter;
        private final long[] buffer;

        private long length = -1;

        private MyLongIla(LongIla ila, LongFilter filter, long[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(long[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            LongIlaIterator oii = new LongIlaIterator(LongIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                long node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                LongIlaIterator oii = new LongIlaIterator(ila, buffer.clone());

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
