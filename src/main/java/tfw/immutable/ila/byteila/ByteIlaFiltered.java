package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaFiltered {
    private ByteIlaFiltered() {
        // non-instantiable class
    }

    public static interface ByteFilter {
        public boolean matches(byte value);
    }

    public static ByteIla create(ByteIla ila, ByteFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyByteIla(ila, filter);
    }

    private static class MyByteIla implements ByteIla, ImmutableLongArray {
        private final ByteIla ila;
        private final ByteFilter filter;

        private long length = -1;

        private MyByteIla(ByteIla ila, ByteFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final byte[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final byte[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            byte[] result = new byte[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(byte[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            ByteIlaIterator oii = new ByteIlaIterator(ByteIlaSegment.create(ila, start));

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                byte node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                ByteIlaIterator oii = new ByteIlaIterator(ila);

                try {
                    for (int i = 0; oii.hasNext(); i++) {
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
