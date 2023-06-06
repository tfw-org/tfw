package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class DoubleIlaClip {
    private DoubleIlaClip() {}

    public static DoubleIla create(DoubleIla doubleIla, double min, double max) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertLessThan(min, max, "min", "max");

        return (new MyDoubleIla(doubleIla, min, max));
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla doubleIla;
        private final double min;
        private final double max;

        public MyDoubleIla(DoubleIla doubleIla, double min, double max) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.min = min;
            this.max = max;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator dii = new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length));

            for (int i = 0; i < length; i++) {
                double d = dii.next();

                d = (d < min) ? min : d;
                d = (d > max) ? max : d;

                array[offset + (i * stride)] = d;
            }
        }
    }
}
