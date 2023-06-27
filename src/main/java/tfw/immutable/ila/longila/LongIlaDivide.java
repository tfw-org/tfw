package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaDivide {
    private LongIlaDivide() {
        // non-instantiable class
    }

    public static LongIla create(LongIla leftIla, LongIla rightIla) {
        return create(leftIla, rightIla, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(LongIla leftIla, LongIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(leftIla, rightIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla leftIla;
        private final LongIla rightIla;
        private final int bufferSize;

        MyLongIla(LongIla leftIla, LongIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator li = new LongIlaIterator(LongIlaSegment.create(leftIla, start, length), bufferSize);
            LongIlaIterator ri = new LongIlaIterator(LongIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride) {
                array[ii] = (long) (li.next() / ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
