package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.doubleilm.DoubleIlm;

public class IntIlmFromCastDoubleIlm {
    private IntIlmFromCastDoubleIlm() {}

    public static IntIlm create(DoubleIlm doubleIlm) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");

        return new IntIlmImpl(doubleIlm);
    }

    private static class IntIlmImpl extends AbstractIntIlm {
        private final DoubleIlm doubleIlm;

        private double[] buffer = new double[0];

        private IntIlmImpl(DoubleIlm doubleIlm) {
            this.doubleIlm = doubleIlm;
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
        protected void getImpl(int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            if (buffer.length < doubleIlm.width()) {
                buffer = new double[(int) doubleIlm.width()];
            }
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.get(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = (int) buffer[j];
                }
            }
        }
    }
}
