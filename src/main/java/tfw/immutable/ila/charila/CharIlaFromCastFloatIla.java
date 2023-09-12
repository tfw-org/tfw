package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;
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
            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return floatIla.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (char) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
