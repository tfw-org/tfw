package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class FloatIlaFromCastShortIla {
    private FloatIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static FloatIla create(ShortIla shortIla, int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(shortIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyFloatIla(ShortIla shortIla, int bufferSize) {
            super(shortIla.length());

            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ShortIlaIterator fi =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (float) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
