package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedFloatIlaFromFloatIla {
    private StridedFloatIlaFromFloatIla() {}

    public static StridedFloatIla create(final FloatIla ila, final float[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedFloatIlaImpl(ila, buffer);
    }

    private static class StridedFloatIlaImpl extends AbstractStridedFloatIla {
        private final FloatIla ila;
        private final float[] buffer;

        public StridedFloatIlaImpl(final FloatIla ila, final float[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final float[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final FloatIla segmentFloatIla =
                    start == 0 && length == ila.length() ? ila : FloatIlaSegment.create(ila, start, length);
            final FloatIlaIterator bii = new FloatIlaIterator(segmentFloatIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
