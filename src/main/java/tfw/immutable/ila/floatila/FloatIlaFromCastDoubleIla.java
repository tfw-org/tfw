package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class FloatIlaFromCastDoubleIla {
    private FloatIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static FloatIla create(DoubleIla doubleIla, int bufferSize) {
        return new FloatIlaImpl(doubleIla, bufferSize);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        private FloatIlaImpl(DoubleIla doubleIla, int bufferSize) {
            Argument.assertNotNull(doubleIla, "doubleIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (float) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
