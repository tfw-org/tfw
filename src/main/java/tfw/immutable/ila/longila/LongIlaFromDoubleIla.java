package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class LongIlaFromDoubleIla {
    private LongIlaFromDoubleIla() {}

    public static LongIla create(final DoubleIla doubleIla, final int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new LongIlaImpl(doubleIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        private LongIlaImpl(final DoubleIla doubleIla, final int bufferSize) {
            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator dii =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Double.doubleToRawLongBits(dii.next());
            }
        }
    }
}
