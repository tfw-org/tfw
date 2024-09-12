package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class DoubleIlaFromLongIla {
    private DoubleIlaFromLongIla() {}

    public static DoubleIla create(final LongIla longIla, final int bufferSize) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new DoubleIlaImpl(longIla, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final LongIla longIla;
        private final int bufferSize;

        private DoubleIlaImpl(final LongIla longIla, final int bufferSize) {
            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return longIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            LongIlaIterator lii =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start, length), new long[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Double.longBitsToDouble(lii.next());
            }
        }
    }
}
