package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

/**
 *
 * @immutables.types=numericnotfloat
 */
public final class IntIlaFromCastFloatIla {
    private IntIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static IntIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(floatIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyIntIla(FloatIla floatIla, int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
