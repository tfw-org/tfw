package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class LongIlaFromDoubleIla {
    private LongIlaFromDoubleIla() {}

    public static LongIla create(DoubleIla doubleIla) {
        Argument.assertNotNull(doubleIla, "doubleIla");

        return new MyLongIla(doubleIla);
    }

    private static class MyLongIla extends AbstractLongIla {
        private DoubleIla doubleIla;

        MyLongIla(DoubleIla doubleIla) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator dii = new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Double.doubleToRawLongBits(dii.next());
            }
        }
    }
}
