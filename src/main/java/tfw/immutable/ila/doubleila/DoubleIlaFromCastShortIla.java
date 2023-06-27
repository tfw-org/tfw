package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

/**
 *
 * @immutables.types=numericnotshort
 */
public final class DoubleIlaFromCastShortIla {
    private DoubleIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static DoubleIla create(ShortIla shortIla) {
        return create(shortIla, ShortIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(ShortIla shortIla, int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(shortIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyDoubleIla(ShortIla shortIla, int bufferSize) {
            super(shortIla.length());

            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ShortIlaIterator fi = new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
