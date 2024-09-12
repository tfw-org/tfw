package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaRemove {
    private LongIlaRemove() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long index) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new LongIlaImpl(ila, index);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long index;

        private LongIlaImpl(LongIla ila, long index) {
            this.ila = ila;
            this.index = index;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() - 1;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            final long startPlusLength = start + length;

            if ((index - 1) < start) {
                ila.get(array, offset, start + 1, length);
            } else if ((index + 1) > startPlusLength) {
                ila.get(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                ila.get(array, offset, start, indexMinusStart);
                ila.get(array, offset + indexMinusStart, index + 1, length - indexMinusStart);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
