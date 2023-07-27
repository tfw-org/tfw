package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class FloatIlaSubtract {
    private FloatIlaSubtract() {
        // non-instantiable class
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

        protected void toArrayImpl(float[] array, int offset, long ilaStart, int length) throws DataInvalidException {
            FloatIlaIterator li =
                    new FloatIlaIterator(FloatIlaSegment.create(leftIla, ilaStart, length), new float[bufferSize]);
            FloatIlaIterator ri =
                    new FloatIlaIterator(FloatIlaSegment.create(rightIla, ilaStart, length), new float[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = li.next() - ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
