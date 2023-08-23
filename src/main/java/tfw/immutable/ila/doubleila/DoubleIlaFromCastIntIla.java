package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
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
            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return intIla.length();
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long start, int length) throws IOException {
            IntIlaIterator fi = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
