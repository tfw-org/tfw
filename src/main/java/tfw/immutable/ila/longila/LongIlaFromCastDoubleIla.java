package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

/**
 *
 * @immutables.types=numericnotdouble
 */
public final class LongIlaFromCastDoubleIla {
    private LongIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static LongIla create(DoubleIla doubleIla) {
        return create(doubleIla, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(DoubleIla doubleIla, int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(doubleIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyLongIla(DoubleIla doubleIla, int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator fi = new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
