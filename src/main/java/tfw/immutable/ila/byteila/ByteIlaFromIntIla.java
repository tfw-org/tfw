package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class ByteIlaFromIntIla {
    private ByteIlaFromIntIla() {}

    public static ByteIla create(final IntIla intIla, final int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new ByteIlaImpl(intIla, bufferSize);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final IntIla intIla;
        private final int bufferSize;

        private ByteIlaImpl(final IntIla intIla, final int bufferSize) {
            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return intIla.length() * 4;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            IntIlaIterator iii = new IntIlaIterator(
                    IntIlaSegment.create(intIla, start / 4, intIla.length() - start / 4), new int[bufferSize]);
            int col = (int) (start % 4);

            for (int totalElements = 0; totalElements < length; ) {
                int value = iii.next();

                for (int c = col; c < 4 && totalElements < length; c++) {
                    array[offset + totalElements++] = (byte) ((value >>> (24 - (8 * c))) & 0xFF);
                }

                col = 0;
            }
        }
    }
}
