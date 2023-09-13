package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleIlmClip {
    private DoubleIlmClip() {}

    public static DoubleIlm create(DoubleIlm doubleIlm, double min, double max) throws IOException {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        Argument.assertLessThan(min, max, "min", "max");

        return new MyDoubleIlm(doubleIlm, min, max);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final double[] buffer;
        private final double min;
        private final double max;

        public MyDoubleIlm(DoubleIlm doubleIlm, double min, double max) throws IOException {
            this.doubleIlm = doubleIlm;
            this.buffer = new double[(int) doubleIlm.width()];
            this.min = min;
            this.max = max;
        }

        @Override
        protected long widthImpl() throws IOException {
            return doubleIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return doubleIlm.height();
        }

        @Override
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.get(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    double d = buffer[j];

                    d = d < min ? min : d;
                    d = d > max ? max : d;

                    array[offset + (i * colCount) + j] = d;
                }
            }
        }
    }
}
