package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaFiltered {
    private FloatIlaFiltered() {
        // non-instantiable class
    }

    public static interface FloatFilter {
        public boolean matches(float value);
    }

    public static FloatIla create(FloatIla ila, FloatFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyFloatIla(ila, filter);
    }

    private static class MyFloatIla implements FloatIla, ImmutableLongArray, ImmutableProxy {
        private final FloatIla ila;
        private final FloatFilter filter;

        private long length = -1;

        private MyFloatIla(FloatIla ila, FloatFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final float[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final float[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            float[] result = new float[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(float[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            FloatIlaIterator oii = new FloatIlaIterator(FloatIlaSegment.create(ila, start));

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                float node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                FloatIlaIterator oii = new FloatIlaIterator(ila);

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

        public Map<String, Object> getParameters() {
            calculateLength();

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "FloatIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
