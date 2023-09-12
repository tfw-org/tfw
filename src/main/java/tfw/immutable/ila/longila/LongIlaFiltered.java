package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

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

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final LongFilter filter;
        private final long[] buffer;

        private MyLongIla(LongIla ila, LongFilter filter, long[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            LongIlaIterator oii = new LongIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(long[] array, int offset, long start, int length) throws IOException {
            LongIlaIterator oii = new LongIlaIterator(LongIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                long node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
