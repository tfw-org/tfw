package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class ShortIlaFromByteIla {
    private ShortIlaFromByteIla() {}

    public static ShortIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new ShortIlaImpl(byteIla, bufferSize);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private ShortIlaImpl(final ByteIla byteIla, final int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length() / 2;
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 2 * start, 2 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = (short) (((bii.next() & 0xFF) << 8) | (bii.next() & 0xFF));
            }
        }
    }
}
