package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class DoubleIlaFromLongIla {
    private DoubleIlaFromLongIla() {}

    public static DoubleIla create(LongIla longIla) {
        Argument.assertNotNull(longIla, "longIla");

        return new MyDoubleIla(longIla);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private LongIla longIla;

        MyDoubleIla(LongIla longIla) {
            super(longIla.length());

            this.longIla = longIla;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator lii = new LongIlaIterator(LongIlaSegment.create(longIla, start, length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Double.longBitsToDouble(lii.next());
            }
        }
    }
}
