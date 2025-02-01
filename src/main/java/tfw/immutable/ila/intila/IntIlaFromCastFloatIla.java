package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromCastFloatIla {
    private IntIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static IntIla create(FloatIla floatIla, int bufferSize) {
        return new IntIlaImpl(floatIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        private IntIlaImpl(FloatIla floatIla, int bufferSize) {
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
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
