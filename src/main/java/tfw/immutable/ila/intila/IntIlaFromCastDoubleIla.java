package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
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
            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
