package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class IntIlaFromByteIla {
    private IntIlaFromByteIla() {}

    public static IntIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(byteIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyIntIla(final ByteIla byteIla, final int bufferSize) {
            super(byteIla.length() / 4);

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, 4 * start, 4 * length), new byte[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = ((bii.next() & 0xFF) << 24)
                        | ((bii.next() & 0xFF) << 16)
                        | ((bii.next() & 0xFF) << 8)
                        | ((bii.next() & 0xFF));
            }
        }
    }
}
