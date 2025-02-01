package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class ShortIlaFromCastDoubleIla {
    private ShortIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static ShortIla create(DoubleIla doubleIla, int bufferSize) {
        return new ShortIlaImpl(doubleIla, bufferSize);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        private ShortIlaImpl(DoubleIla doubleIla, int bufferSize) {
            Argument.assertNotNull(doubleIla, "doubleIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
