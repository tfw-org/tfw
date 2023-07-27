package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class DoubleIlaClip {
    private DoubleIlaClip() {}

    public static DoubleIla create(
            final DoubleIla doubleIla, final double min, final double max, final int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertLessThan(min, max, "min", "max");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(doubleIla, min, max, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla doubleIla;
        private final double min;
        private final double max;
        private final int bufferSize;

        public MyDoubleIla(final DoubleIla doubleIla, final double min, final double max, final int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.min = min;
            this.max = max;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, long start, int length) throws DataInvalidException {
            DoubleIlaIterator dii =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int i = 0; i < length; i++) {
                double d = dii.next();

                d = d < min ? min : d;
                d = d > max ? max : d;

                array[offset + i] = d;
            }
        }
    }
}
