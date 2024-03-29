package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaDivide {
    private DoubleIlaDivide() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) throws IOException {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new DoubleIlaImpl(leftIla, rightIla, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla leftIla;
        private final DoubleIla rightIla;
        private final int bufferSize;

        private DoubleIlaImpl(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long ilaStart, int length) throws IOException {
            DoubleIlaIterator li =
                    new DoubleIlaIterator(DoubleIlaSegment.create(leftIla, ilaStart, length), new double[bufferSize]);
            DoubleIlaIterator ri =
                    new DoubleIlaIterator(DoubleIlaSegment.create(rightIla, ilaStart, length), new double[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = li.next() / ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
