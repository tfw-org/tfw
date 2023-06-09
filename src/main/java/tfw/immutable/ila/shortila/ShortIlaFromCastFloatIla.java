package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

/**
 *
 * @immutables.types=numericnotfloat
 */
public final class ShortIlaFromCastFloatIla {
    private ShortIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static ShortIla create(FloatIla floatIla) {
        return create(floatIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ShortIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(floatIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyShortIla(FloatIla floatIla, int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fi = new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
