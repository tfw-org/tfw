package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromFloatIla {
    private IntIlaFromFloatIla() {}

    public static IntIla create(final FloatIla floatIla, final int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(floatIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyIntIla(final FloatIla floatIla, final int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fii =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Float.floatToRawIntBits(fii.next());
            }
        }
    }
}
