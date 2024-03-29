package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class InterleavedMagnitudeDoubleIlm {
    private InterleavedMagnitudeDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm doubleIlm) throws IOException {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        Argument.assertEquals(doubleIlm.width() % 2, 0, "doubleIlm.width()", "0");

        return new DoubleIlmImpl(doubleIlm);
    }

    private static class DoubleIlmImpl extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final double[] buffer;

        private DoubleIlmImpl(DoubleIlm doubleIlm) throws IOException {
            this.doubleIlm = doubleIlm;
            this.buffer = new double[(int) doubleIlm.width()];
        }

        @Override
        protected long widthImpl() throws IOException {
            return doubleIlm.width() / 2;
        }

        @Override
        protected long heightImpl() throws IOException {
            return doubleIlm.height();
        }

        @Override
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.get(buffer, 0, rowStart + i, colStart * 2, 1, colCount * 2);

                for (int j = 0; j < colCount; j++) {
                    double real = buffer[j * 2];
                    double imag = buffer[j * 2 + 1];

                    array[offset + (i * colCount) + j] = real * real + imag * imag;
                }
            }
        }
    }
}
