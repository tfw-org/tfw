package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedIntIlaFromIntIla {
    private StridedIntIlaFromIntIla() {}

    public static StridedIntIla create(final IntIla ila, final int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedIntIlaImpl(ila, buffer);
    }

    private static class StridedIntIlaImpl extends AbstractStridedIntIla {
        private final IntIla ila;
        private final int[] buffer;

        public StridedIntIlaImpl(final IntIla ila, final int[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final int[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final IntIla segmentIntIla =
                    start == 0 && length == ila.length() ? ila : IntIlaSegment.create(ila, start, length);
            final IntIlaIterator bii = new IntIlaIterator(segmentIntIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
