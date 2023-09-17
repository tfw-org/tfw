package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaFiltered {
    private IntIlaFiltered() {
        // non-instantiable class
    }

    public interface IntFilter {
        boolean matches(int value);
    }

    public static IntIla create(IntIla ila, IntFilter filter, int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new IntIlaImpl(ila, filter, buffer);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla ila;
        private final IntFilter filter;
        private final int[] buffer;

        private IntIlaImpl(IntIla ila, IntFilter filter, int[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            IntIlaIterator oii = new IntIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(int[] array, int offset, long start, int length) throws IOException {
            IntIlaIterator oii = new IntIlaIterator(IntIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                int node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
