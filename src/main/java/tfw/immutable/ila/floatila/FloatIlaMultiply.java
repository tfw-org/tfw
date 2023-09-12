package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaMultiply {
    private FloatIlaMultiply() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla, int bufferSize) throws IOException {
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
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long ilaStart, int length) throws IOException {
            FloatIlaIterator li =
                    new FloatIlaIterator(FloatIlaSegment.create(leftIla, ilaStart, length), new float[bufferSize]);
            FloatIlaIterator ri =
                    new FloatIlaIterator(FloatIlaSegment.create(rightIla, ilaStart, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = li.next() * ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
