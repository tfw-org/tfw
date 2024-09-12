package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class LongIlaFromByteIla {
    private LongIlaFromByteIla() {}

    public static LongIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new LongIlaImpl(byteIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private LongIlaImpl(final ByteIla byteIla, final int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length() / 8;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 8 * start, 8 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = (((long) bii.next() & 0xFF) << 56)
                        | (((long) bii.next() & 0xFF) << 48)
                        | (((long) bii.next() & 0xFF) << 40)
                        | (((long) bii.next() & 0xFF) << 32)
                        | (((long) bii.next() & 0xFF) << 24)
                        | (((long) bii.next() & 0xFF) << 16)
                        | (((long) bii.next() & 0xFF) << 8)
                        | ((long) bii.next() & 0xFF);
            }
        }
    }
}
