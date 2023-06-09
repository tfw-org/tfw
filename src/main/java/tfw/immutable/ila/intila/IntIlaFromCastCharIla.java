package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

/**
 *
 * @immutables.types=numericnotchar
 */
public final class IntIlaFromCastCharIla {
    private IntIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static IntIla create(CharIla charIla) {
        return create(charIla, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(charIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final CharIla charIla;
        private final int bufferSize;

        MyIntIla(CharIla charIla, int bufferSize) {
            super(charIla.length());

            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            CharIlaIterator fi = new CharIlaIterator(CharIlaSegment.create(charIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
