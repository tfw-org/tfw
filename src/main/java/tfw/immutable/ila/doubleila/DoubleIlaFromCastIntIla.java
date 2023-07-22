package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class DoubleIlaFromCastIntIla {
    private DoubleIlaFromCastIntIla() {
        // non-instantiable class
    }

    public static DoubleIla create(IntIla intIla, int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(intIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final IntIla intIla;
        private final int bufferSize;

        MyDoubleIla(IntIla intIla, int bufferSize) {
            super(intIla.length());

            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator fi = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
