package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

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

        return new BooleanIlaImpl(ila, filter, buffer);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final BooleanFilter filter;
        private final boolean[] buffer;

        private BooleanIlaImpl(BooleanIla ila, BooleanFilter filter, boolean[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            BooleanIlaIterator oii = new BooleanIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
            BooleanIlaIterator oii = new BooleanIlaIterator(BooleanIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                boolean node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
