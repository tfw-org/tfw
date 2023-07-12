package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class FloatIlaMultiply {
    private FloatIlaMultiply() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla) {
        return create(leftIla, rightIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(leftIla, rightIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla leftIla;
        private final FloatIla rightIla;
        private final int bufferSize;

        MyFloatIla(FloatIla leftIla, FloatIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator li = new FloatIlaIterator(FloatIlaSegment.create(leftIla, start, length), bufferSize);
            FloatIlaIterator ri = new FloatIlaIterator(FloatIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = li.next() * ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
