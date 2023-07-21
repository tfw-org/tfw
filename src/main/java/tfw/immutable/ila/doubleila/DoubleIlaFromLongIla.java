package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class DoubleIlaFromLongIla {
    private DoubleIlaFromLongIla() {}

    public static DoubleIla create(final LongIla longIla, final int bufferSize) {
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(longIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final LongIla longIla;
        private final int bufferSize;

        MyDoubleIla(final LongIla longIla, final int bufferSize) {
            super(longIla.length());

            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator lii =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start, length), new long[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Double.longBitsToDouble(lii.next());
            }
        }
    }
}
