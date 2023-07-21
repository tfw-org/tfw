package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaFiltered {
    private BooleanIlaFiltered() {
        // non-instantiable class
    }

    public interface BooleanFilter {
        boolean matches(boolean value);
    }

    public static BooleanIla create(BooleanIla ila, BooleanFilter filter, boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyBooleanIla(ila, filter, buffer);
    }

    private static class MyBooleanIla implements BooleanIla {
        private final BooleanIla ila;
        private final BooleanFilter filter;
        private final boolean[] buffer;

        private long length = -1;

        private MyBooleanIla(BooleanIla ila, BooleanFilter filter, boolean[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(boolean[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(boolean[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            BooleanIlaIterator oii = new BooleanIlaIterator(BooleanIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                boolean node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                BooleanIlaIterator oii = new BooleanIlaIterator(ila, buffer.clone());

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
