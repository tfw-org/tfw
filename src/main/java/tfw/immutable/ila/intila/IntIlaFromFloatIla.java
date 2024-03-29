package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromFloatIla {
    private IntIlaFromFloatIla() {}

    public static IntIla create(final FloatIla floatIla, final int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new IntIlaImpl(floatIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        private IntIlaImpl(final FloatIla floatIla, final int bufferSize) {
            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return floatIla.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator fii =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Float.floatToRawIntBits(fii.next());
            }
        }
    }
}
