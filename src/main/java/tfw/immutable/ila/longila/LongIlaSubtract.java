package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaSubtract {
    private LongIlaSubtract() {
        // non-instantiable class
    }

    public static LongIla create(LongIla leftIla, LongIla rightIla, int bufferSize) throws IOException {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new LongIlaImpl(leftIla, rightIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla leftIla;
        private final LongIla rightIla;
        private final int bufferSize;

        private LongIlaImpl(LongIla leftIla, LongIla rightIla, int bufferSize) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long ilaStart, int length) throws IOException {
            LongIlaIterator li =
                    new LongIlaIterator(LongIlaSegment.create(leftIla, ilaStart, length), new long[bufferSize]);
            LongIlaIterator ri =
                    new LongIlaIterator(LongIlaSegment.create(rightIla, ilaStart, length), new long[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = li.next() - ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
