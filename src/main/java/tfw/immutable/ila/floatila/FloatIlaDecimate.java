package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaDecimate {
    private FloatIlaDecimate() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long factor) {
        return create(ila, factor, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(FloatIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(ila, factor, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final long factor;
        private final int bufferSize;

        MyFloatIla(FloatIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final FloatIla segment = FloatIlaSegment.create(ila, segmentStart, segmentLength);
            final FloatIlaIterator fi = new FloatIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
