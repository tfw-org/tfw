package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class IntIlaFromByteIla {
    private IntIlaFromByteIla() {}

    public static IntIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new IntIlaImpl(byteIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private IntIlaImpl(final ByteIla byteIla, final int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length() / 4;
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 4 * start, 4 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = ((bii.next() & 0xFF) << 24)
                        | ((bii.next() & 0xFF) << 16)
                        | ((bii.next() & 0xFF) << 8)
                        | (bii.next() & 0xFF);
            }
        }
    }
}
