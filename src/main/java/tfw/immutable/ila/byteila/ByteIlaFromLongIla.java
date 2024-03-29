package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class ByteIlaFromLongIla {
    private ByteIlaFromLongIla() {}

    public static ByteIla create(final LongIla longIla, final int bufferSize) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new ByteIlaImpl(longIla, bufferSize);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final LongIla longIla;
        private final int bufferSize;

        private ByteIlaImpl(final LongIla longIla, final int bufferSize) {
            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return longIla.length() * 8;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            LongIlaIterator lii = new LongIlaIterator(
                    LongIlaSegment.create(longIla, start / 8, longIla.length() - start / 8), new long[bufferSize]);
            int col = (int) (start % 8);

            for (int totalElements = 0; totalElements < length; ) {
                long value = lii.next();

                for (int c = col; c < 8 && totalElements < length; c++) {
                    array[offset + totalElements++] = (byte) ((value >>> (56 - (8 * c))) & 0xFF);
                }

                col = 0;
            }
        }
    }
}
