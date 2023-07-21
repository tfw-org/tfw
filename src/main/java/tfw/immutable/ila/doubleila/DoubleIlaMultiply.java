package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class DoubleIlaMultiply {
    private DoubleIlaMultiply() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(leftIla, rightIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla leftIla;
        private final DoubleIla rightIla;
        private final int bufferSize;

        MyDoubleIla(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long ilaStart, int length)
                throws DataInvalidException {
            DoubleIlaIterator li =
                    new DoubleIlaIterator(DoubleIlaSegment.create(leftIla, ilaStart, length), new double[bufferSize]);
            DoubleIlaIterator ri =
                    new DoubleIlaIterator(DoubleIlaSegment.create(rightIla, ilaStart, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = li.next() * ri.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
