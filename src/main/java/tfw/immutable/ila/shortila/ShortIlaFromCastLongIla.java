package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

/**
 *
 * @immutables.types=numericnotlong
 */
public final class ShortIlaFromCastLongIla {
    private ShortIlaFromCastLongIla() {
        // non-instantiable class
    }

    public static ShortIla create(LongIla longIla, int bufferSize) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(longIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final LongIla longIla;
        private final int bufferSize;

        MyShortIla(LongIla longIla, int bufferSize) {
            super(longIla.length());

            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator fi =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start, length), new long[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
