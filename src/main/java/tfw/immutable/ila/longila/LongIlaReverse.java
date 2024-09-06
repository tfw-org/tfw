package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaReverse {
    private LongIlaReverse() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, final long[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new LongIlaImpl(ila, buffer);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long[] buffer;

        private LongIlaImpl(LongIla ila, final long[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            final StridedLongIla stridedLongIla = StridedLongIlaFromLongIla.create(ila, buffer.clone());

            stridedLongIla.get(array, offset + length - 1, -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
