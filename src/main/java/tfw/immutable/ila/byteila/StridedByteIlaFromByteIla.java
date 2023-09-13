package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class StridedByteIlaFromByteIla {
    private StridedByteIlaFromByteIla() {}

    public static StridedByteIla create(final ByteIla ila, final byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedByteIla(ila, buffer);
    }

    private static class MyStridedByteIla extends AbstractStridedByteIla {
        private final ByteIla ila;
        private final byte[] buffer;

        public MyStridedByteIla(final ByteIla ila, final byte[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        public long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        public void getImpl(final byte[] array, final int offset, final int stride, final long start, int length)
                throws IOException {
            final ByteIla segmentByteIla =
                    start == 0 && length == ila.length() ? ila : ByteIlaSegment.create(ila, start, length);
            final ByteIlaIterator bii = new ByteIlaIterator(segmentByteIla, buffer.clone());

            for (int i = 0, ii = offset; i < length; i++, ii += stride) {
                array[ii] = bii.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
