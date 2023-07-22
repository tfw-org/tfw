package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class CharIlaFromCastByteIla {
    private CharIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static CharIla create(ByteIla byteIla, int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(byteIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyCharIla(ByteIla byteIla, int bufferSize) {
            super(byteIla.length());

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator fi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (char) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
