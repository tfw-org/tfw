package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

public final class FloatIlaFiltered {
    private FloatIlaFiltered() {
        // non-instantiable class
    }

    public interface FloatFilter {
        boolean matches(float value);
    }

    public static FloatIla create(FloatIla ila, FloatFilter filter, float[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyFloatIla(ila, filter, buffer);
    }

    private static class MyFloatIla implements FloatIla {
        private final FloatIla ila;
        private final FloatFilter filter;
        private final float[] buffer;

        private long length = -1;

        private MyFloatIla(FloatIla ila, FloatFilter filter, float[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
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

            FloatIlaIterator oii = new FloatIlaIterator(FloatIlaSegment.create(ila, start), buffer.clone());

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
                FloatIlaIterator oii = new FloatIlaIterator(ila, buffer.clone());

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
