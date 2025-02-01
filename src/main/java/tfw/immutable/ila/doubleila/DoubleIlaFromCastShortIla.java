package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class DoubleIlaFromCastShortIla {
    private DoubleIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static DoubleIla create(ShortIla shortIla, int bufferSize) {
        return new DoubleIlaImpl(shortIla, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        private DoubleIlaImpl(ShortIla shortIla, int bufferSize) {
            Argument.assertNotNull(shortIla, "shortIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return shortIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator fi =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
