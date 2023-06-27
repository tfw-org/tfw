package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

/**
 *
 * @immutables.types=numericnotfloat
 */
public final class LongIlaFromCastFloatIla {
    private LongIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static LongIla create(FloatIla floatIla) {
        return create(floatIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(floatIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyLongIla(FloatIla floatIla, int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fi = new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
