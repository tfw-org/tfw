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

        return new MyLongIla(doubleIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyLongIla(final DoubleIla doubleIla, final int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator dii =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Double.doubleToRawLongBits(dii.next());
            }
        }
    }
}
