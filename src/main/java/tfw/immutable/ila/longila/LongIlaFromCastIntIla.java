package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class LongIlaFromCastIntIla {
    private LongIlaFromCastIntIla() {
        // non-instantiable class
    }

    public static LongIla create(IntIla intIla, int bufferSize) {
        return new LongIlaImpl(intIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final IntIla intIla;
        private final int bufferSize;

        private LongIlaImpl(IntIla intIla, int bufferSize) {
            Argument.assertNotNull(intIla, "intIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return intIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            IntIlaIterator fi = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), new int[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
