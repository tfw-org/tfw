package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedLongIlaFromLongIla {
    private StridedLongIlaFromLongIla() {}

    public static StridedLongIla create(final LongIla ila, final long[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedLongIla(ila, buffer);
    }

    private static class MyStridedLongIla extends AbstractStridedLongIla {
        private final LongIla ila;
        private final long[] buffer;

        public MyStridedLongIla(final LongIla ila, final long[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final long[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final LongIla segmentLongIla =
                    start == 0 && length == ila.length() ? ila : LongIlaSegment.create(ila, start, length);
            final LongIlaIterator bii = new LongIlaIterator(segmentLongIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
