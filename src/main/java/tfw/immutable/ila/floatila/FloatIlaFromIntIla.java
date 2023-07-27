package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class FloatIlaFromIntIla {
    private FloatIlaFromIntIla() {}

    public static FloatIla create(final IntIla intIla, final int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(intIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final IntIla intIla;
        private final int bufferSize;

        MyFloatIla(final IntIla intIla, final int bufferSize) {
            super(intIla.length());

            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, long start, int length) throws DataInvalidException {
            IntIlaIterator iii = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int i = 0; i < length; i++) {
                array[offset + i] = Float.intBitsToFloat(iii.next());
            }
        }
    }
}
