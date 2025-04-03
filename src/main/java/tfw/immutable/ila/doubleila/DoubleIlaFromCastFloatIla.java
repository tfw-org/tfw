package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class DoubleIlaFromCastFloatIla {
    private DoubleIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static DoubleIla create(FloatIla floatIla, int bufferSize) {
        return new DoubleIlaImpl(floatIla, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        private DoubleIlaImpl(FloatIla floatIla, int bufferSize) {
            Argument.assertNotNull(floatIla, "floatIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return floatIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
