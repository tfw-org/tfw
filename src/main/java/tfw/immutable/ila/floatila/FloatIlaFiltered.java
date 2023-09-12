package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

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

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final FloatFilter filter;
        private final float[] buffer;

        private MyFloatIla(FloatIla ila, FloatFilter filter, float[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            FloatIlaIterator oii = new FloatIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(float[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator oii = new FloatIlaIterator(FloatIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                float node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
