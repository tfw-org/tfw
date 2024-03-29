package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaMultiply {
    private IntIlaMultiply() {
        // non-instantiable class
    }

    public static IntIla create(IntIla leftIla, IntIla rightIla, int bufferSize) throws IOException {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new IntIlaImpl(leftIla, rightIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla leftIla;
        private final IntIla rightIla;
        private final int bufferSize;

        private IntIlaImpl(IntIla leftIla, IntIla rightIla, int bufferSize) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long ilaStart, int length) throws IOException {
            IntIlaIterator li =
                    new IntIlaIterator(IntIlaSegment.create(leftIla, ilaStart, length), new int[bufferSize]);
            IntIlaIterator ri =
                    new IntIlaIterator(IntIlaSegment.create(rightIla, ilaStart, length), new int[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = li.next() * ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
