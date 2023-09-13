package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedDoubleIlaFromDoubleIla {
    private StridedDoubleIlaFromDoubleIla() {}

    public static StridedDoubleIla create(final DoubleIla ila, final double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedDoubleIla(ila, buffer);
    }

    private static class MyStridedDoubleIla extends AbstractStridedDoubleIla {
        private final DoubleIla ila;
        private final double[] buffer;

        public MyStridedDoubleIla(final DoubleIla ila, final double[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final double[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final DoubleIla segmentDoubleIla =
                    start == 0 && length == ila.length() ? ila : DoubleIlaSegment.create(ila, start, length);
            final DoubleIlaIterator bii = new DoubleIlaIterator(segmentDoubleIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
