package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

/**
 *
 * @immutables.types=numericnotlong
 */
public final class FloatIlaFromCastLongIla {
    private FloatIlaFromCastLongIla() {
        // non-instantiable class
    }

    public static FloatIla create(LongIla longIla) {
        return create(longIla, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(LongIla longIla, int bufferSize) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(longIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final LongIla longIla;
        private final int bufferSize;

        MyFloatIla(LongIla longIla, int bufferSize) {
            super(longIla.length());

            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator fi = new LongIlaIterator(LongIlaSegment.create(longIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (float) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
