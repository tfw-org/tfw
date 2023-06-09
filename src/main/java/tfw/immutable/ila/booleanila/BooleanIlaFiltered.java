package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

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

    public static BooleanIla create(BooleanIla ila, BooleanFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyBooleanIla(ila, filter);
    }

    private static class MyBooleanIla implements BooleanIla, ImmutableLongArray {
        private final BooleanIla ila;
        private final BooleanFilter filter;

        private long length = -1;

        private MyBooleanIla(BooleanIla ila, BooleanFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final boolean[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final boolean[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            boolean[] result = new boolean[length];

            toArray(result, 0, start, length);

            return result;
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

            BooleanIlaIterator oii = new BooleanIlaIterator(BooleanIlaSegment.create(ila, start));

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
                BooleanIlaIterator oii = new BooleanIlaIterator(ila);

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
