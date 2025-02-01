package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class FloatIlaFromCastShortIla {
    private FloatIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static FloatIla create(ShortIla shortIla, int bufferSize) {
        return new FloatIlaImpl(shortIla, bufferSize);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        private FloatIlaImpl(ShortIla shortIla, int bufferSize) {
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
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator fi =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (float) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
