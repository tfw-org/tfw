package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaFiltered {
    private IntIlaFiltered() {
        // non-instantiable class
    }

    public interface IntFilter {
        boolean matches(int value);
    }

    public static IntIla create(IntIla ila, IntFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyIntIla(ila, filter);
    }

    private static class MyIntIla implements IntIla, ImmutableLongArray {
        private final IntIla ila;
        private final IntFilter filter;

        private long length = -1;

        private MyIntIla(IntIla ila, IntFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final int[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final int[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            int[] result = new int[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(int[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            IntIlaIterator oii = new IntIlaIterator(IntIlaSegment.create(ila, start));

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                int node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                IntIlaIterator oii = new IntIlaIterator(ila);

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
