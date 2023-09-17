package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleIlaClip {
    private DoubleIlaClip() {}

    public static DoubleIla create(
            final DoubleIla doubleIla, final double min, final double max, final int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertLessThan(min, max, "min", "max");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new DoubleIlaImpl(doubleIla, min, max, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla doubleIla;
        private final double min;
        private final double max;
        private final int bufferSize;

        private DoubleIlaImpl(final DoubleIla doubleIla, final double min, final double max, final int bufferSize) {
            this.doubleIla = doubleIla;
            this.min = min;
            this.max = max;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
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
