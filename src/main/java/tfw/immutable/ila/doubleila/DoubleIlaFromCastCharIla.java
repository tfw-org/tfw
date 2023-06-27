package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

/**
 *
 * @immutables.types=numericnotchar
 */
public final class DoubleIlaFromCastCharIla {
    private DoubleIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static DoubleIla create(CharIla charIla) {
        return create(charIla, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(charIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final CharIla charIla;
        private final int bufferSize;

        MyDoubleIla(CharIla charIla, int bufferSize) {
            super(charIla.length());

            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            CharIlaIterator fi = new CharIlaIterator(CharIlaSegment.create(charIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
