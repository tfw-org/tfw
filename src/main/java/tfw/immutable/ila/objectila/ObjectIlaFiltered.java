package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFiltered {
    private ObjectIlaFiltered() {
        // non-instantiable class
    }

    public interface ObjectFilter {
        boolean matches(Object value);
    }

    public static ObjectIla create(ObjectIla ila, ObjectFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyObjectIla(ila, filter);
    }

    private static class MyObjectIla implements ObjectIla, ImmutableLongArray {
        private final ObjectIla ila;
        private final ObjectFilter filter;

        private long length = -1;

        private MyObjectIla(ObjectIla ila, ObjectFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final Object[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final Object[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            Object[] result = new Object[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(Object[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(Object[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            ObjectIlaIterator oii = new ObjectIlaIterator(ObjectIlaSegment.create(ila, start));

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                Object node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                ObjectIlaIterator oii = new ObjectIlaIterator(ila);

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
