package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

/**
 *
 * @immutables.types=numericnotchar
 */
public final class ByteIlaFromCastCharIla {
    private ByteIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static ByteIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(charIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final CharIla charIla;
        private final int bufferSize;

        MyByteIla(CharIla charIla, int bufferSize) {
            super(charIla.length());

            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            CharIlaIterator fi =
                    new CharIlaIterator(CharIlaSegment.create(charIla, start, length), new char[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
