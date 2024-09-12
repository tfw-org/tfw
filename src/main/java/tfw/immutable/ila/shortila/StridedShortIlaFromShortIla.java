package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedShortIlaFromShortIla {
    private StridedShortIlaFromShortIla() {}

    public static StridedShortIla create(final ShortIla ila, final short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedShortIlaImpl(ila, buffer);
    }

    private static class StridedShortIlaImpl extends AbstractStridedShortIla {
        private final ShortIla ila;
        private final short[] buffer;

        public StridedShortIlaImpl(final ShortIla ila, final short[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final short[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final ShortIla segmentShortIla =
                    start == 0 && length == ila.length() ? ila : ShortIlaSegment.create(ila, start, length);
            final ShortIlaIterator bii = new ShortIlaIterator(segmentShortIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
