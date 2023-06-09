package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

/**
 *
 * @immutables.types=numericnotchar
 */
public final class ShortIlaFromCastCharIla {
    private ShortIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static ShortIla create(CharIla charIla) {
        return create(charIla, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ShortIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(charIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final CharIla charIla;
        private final int bufferSize;

        MyShortIla(CharIla charIla, int bufferSize) {
            super(charIla.length());

            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            CharIlaIterator fi = new CharIlaIterator(CharIlaSegment.create(charIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
