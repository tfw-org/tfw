package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleIlmTenLogTen {
    private DoubleIlmTenLogTen() {}

    public static DoubleIlm create(DoubleIlm doubleIlm) throws IOException {
        Argument.assertNotNull(doubleIlm, "doubleIlm");

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
                    array[offset + (i * colCount) + j] = 10.0 * Math.log10(buffer[j]);
                }
            }
        }
    }
}
