package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedCharIlaFromCharIla {
    private StridedCharIlaFromCharIla() {}

    public static StridedCharIla create(final CharIla ila, final char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedCharIla(ila, buffer);
    }

    private static class MyStridedCharIla extends AbstractStridedCharIla {
        private final CharIla ila;
        private final char[] buffer;

        public MyStridedCharIla(final CharIla ila, final char[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final char[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final CharIla segmentCharIla =
                    start == 0 && length == ila.length() ? ila : CharIlaSegment.create(ila, start, length);
            final CharIlaIterator bii = new CharIlaIterator(segmentCharIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
