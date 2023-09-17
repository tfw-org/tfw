package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.dsp.xform.Ooura1D;

public class RealFftDoubleIlm {
    private RealFftDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm doubleIlm, int fftSize) throws IOException {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        // Check to make sure fftSize is a power of 2.

        return new DoubleIlmImpl(doubleIlm, fftSize);
    }

    private static class DoubleIlmImpl extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final int fftSize;
        private final int dataWidth;
        private final double[] buffer;
        private final int[] ip;
        private final double[] w;

        public DoubleIlmImpl(DoubleIlm doubleIlm, int fftSize) throws IOException {
            this.doubleIlm = doubleIlm;
            this.fftSize = fftSize;
            this.dataWidth = (int) doubleIlm.width();
            this.buffer = new double[fftSize];
            this.ip = new int[2 + (int) Math.ceil(Math.sqrt(fftSize / 2))];
            this.w = new double[fftSize / 2];
        }

        @Override
        protected long widthImpl() throws IOException {
            return fftSize;
        }

        @Override
        protected long heightImpl() throws IOException {
            return doubleIlm.height();
        }

        @Override
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            Argument.assertEquals(colStart, 0, "colStart", "0");
            Argument.assertEquals(colCount, fftSize, "colCount", "fftSize");

            for (int i = 0; i < rowCount; i++) {
                doubleIlm.get(buffer, 0, rowStart + i, 0, 1, dataWidth);
                Ooura1D.rdft(fftSize, 1, buffer, ip, w);

                System.arraycopy(buffer, 0, array, offset + (colCount * i), fftSize);
            }
        }
    }
}
