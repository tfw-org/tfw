package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaDivide {
    private IntIlaDivide() {
        // non-instantiable class
    }

    public static IntIla create(IntIla leftIla, IntIla rightIla) {
        return create(leftIla, rightIla, IntIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(IntIla leftIla, IntIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(leftIla, rightIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla leftIla;
        private final IntIla rightIla;
        private final int bufferSize;

        MyIntIla(IntIla leftIla, IntIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator li = new IntIlaIterator(IntIlaSegment.create(leftIla, start, length), bufferSize);
            IntIlaIterator ri = new IntIlaIterator(IntIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride) {
                array[ii] = li.next() / ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
