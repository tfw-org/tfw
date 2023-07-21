package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class LongIlaFromByteIla {
    private LongIlaFromByteIla() {}

    public static LongIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(byteIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyLongIla(final ByteIla byteIla, final int bufferSize) {
            super(byteIla.length() / 8);

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 8 * start, 8 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = (((long) bii.next() & 0xFF) << 56)
                        | (((long) bii.next() & 0xFF) << 48)
                        | (((long) bii.next() & 0xFF) << 40)
                        | (((long) bii.next() & 0xFF) << 32)
                        | (((long) bii.next() & 0xFF) << 24)
                        | (((long) bii.next() & 0xFF) << 16)
                        | (((long) bii.next() & 0xFF) << 8)
                        | (((long) bii.next() & 0xFF));
            }
        }
    }
}
