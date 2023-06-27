package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class ShortIlaFromByteIla {
    private ShortIlaFromByteIla() {}

    public static ShortIla create(ByteIla byteIla) {
        Argument.assertNotNull(byteIla, "byteIla");

        return new MyShortIla(byteIla);
    }

    private static class MyShortIla extends AbstractShortIla {
        private ByteIla byteIla;

        MyShortIla(ByteIla byteIla) {
            super(byteIla.length() / 2);

            this.byteIla = byteIla;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator bii = new ByteIlaIterator(ByteIlaSegment.create(byteIla, 2 * start, 2 * length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = (short) (((bii.next() & 0xFF) << 8) | ((bii.next() & 0xFF)));
            }
        }
    }
}
