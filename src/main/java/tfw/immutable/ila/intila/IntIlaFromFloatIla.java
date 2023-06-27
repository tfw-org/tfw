package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class IntIlaFromFloatIla {
    private IntIlaFromFloatIla() {}

    public static IntIla create(FloatIla floatIla) {
        Argument.assertNotNull(floatIla, "floatIla");

        return new MyIntIla(floatIla);
    }

    private static class MyIntIla extends AbstractIntIla {
        private FloatIla floatIla;

        MyIntIla(FloatIla floatIla) {
            super(floatIla.length());

            this.floatIla = floatIla;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fii = new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Float.floatToRawIntBits(fii.next());
            }
        }
    }
}
