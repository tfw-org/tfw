package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class ShortIlaFromByteIla {
    private ShortIlaFromByteIla() {}

    public static ShortIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(byteIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyShortIla(final ByteIla byteIla, final int bufferSize) {
            super(byteIla.length() / 2);

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws DataInvalidException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 2 * start, 2 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = (short) (((bii.next() & 0xFF) << 8) | ((bii.next() & 0xFF)));
            }
        }
    }
}
