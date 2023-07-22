package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class IntIlaFromCastDoubleIla {
    private IntIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static IntIla create(DoubleIla doubleIla, int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(doubleIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyIntIla(DoubleIla doubleIla, int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
