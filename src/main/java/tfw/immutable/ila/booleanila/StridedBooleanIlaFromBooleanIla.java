package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedBooleanIlaFromBooleanIla {
    private StridedBooleanIlaFromBooleanIla() {}

    public static StridedBooleanIla create(final BooleanIla ila, final boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedBooleanIlaImpl(ila, buffer);
    }

    private static class StridedBooleanIlaImpl extends AbstractStridedBooleanIla {
        private final BooleanIla ila;
        private final boolean[] buffer;

        public StridedBooleanIlaImpl(final BooleanIla ila, final boolean[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final boolean[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final BooleanIla segmentBooleanIla =
                    start == 0 && length == ila.length() ? ila : BooleanIlaSegment.create(ila, start, length);
            final BooleanIlaIterator bii = new BooleanIlaIterator(segmentBooleanIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
