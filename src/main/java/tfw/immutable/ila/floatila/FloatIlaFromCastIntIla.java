package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

/**
 *
 * @immutables.types=numericnotint
 */
public final class FloatIlaFromCastIntIla {
    private FloatIlaFromCastIntIla() {
        // non-instantiable class
    }

    public static FloatIla create(IntIla intIla, int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(intIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final IntIla intIla;
        private final int bufferSize;

        MyFloatIla(IntIla intIla, int bufferSize) {
            super(intIla.length());

            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator fi = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (float) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
