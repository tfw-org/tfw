package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class CharIlaFromCastFloatIla {
    private CharIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static CharIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(floatIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyCharIla(FloatIla floatIla, int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (char) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
