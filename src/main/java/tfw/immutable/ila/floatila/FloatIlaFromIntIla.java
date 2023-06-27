package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class FloatIlaFromIntIla {
    private FloatIlaFromIntIla() {}

    public static FloatIla create(IntIla intIla) {
        Argument.assertNotNull(intIla, "intIla");

        return new MyFloatIla(intIla);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private IntIla intIla;

        MyFloatIla(IntIla intIla) {
            super(intIla.length());

            this.intIla = intIla;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator iii = new IntIlaIterator(IntIlaSegment.create(intIla, start, length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Float.intBitsToFloat(iii.next());
            }
        }
    }
}
