package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class FloatIlaFromIntIla {
    private FloatIlaFromIntIla() {}

    public static FloatIla create(final IntIla intIla, final int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new FloatIlaImpl(intIla, bufferSize);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final IntIla intIla;
        private final int bufferSize;

        private FloatIlaImpl(final IntIla intIla, final int bufferSize) {
            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return intIla.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            IntIlaIterator iii = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Float.intBitsToFloat(iii.next());
            }
        }
    }
}
