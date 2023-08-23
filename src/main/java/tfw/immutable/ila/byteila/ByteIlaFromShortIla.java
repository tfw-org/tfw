package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class ByteIlaFromShortIla {
    private ByteIlaFromShortIla() {}

    public static ByteIla create(final ShortIla shortIla, final int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(shortIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyByteIla(final ShortIla shortIla, final int bufferSize) {
            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return shortIla.length() * 2;
        }

        @Override
        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator sii = new ShortIlaIterator(
                    ShortIlaSegment.create(shortIla, start / 2, shortIla.length() - start / 2), new short[bufferSize]);
            int col = (int) (start % 2);

            for (int totalElements = 0; totalElements < length; ) {
                int value = sii.next();

                for (int c = col; c < 2 && totalElements < length; c++) {
                    array[offset + totalElements++] = (byte) ((value >>> (8 - (8 * c))) & 0xFF);
                }

                col = 0;
            }
        }
    }
}
