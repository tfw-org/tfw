package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class DoubleIlmTenLogTen {
    private DoubleIlmTenLogTen() {}

    public static DoubleIlm create(DoubleIlm doubleIlm) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");

        return new MyDoubleIlm(doubleIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final double[] buffer;

        public MyDoubleIlm(DoubleIlm doubleIlm) {
            super(doubleIlm.width(), doubleIlm.height());

            this.doubleIlm = doubleIlm;
            this.buffer = new double[(int) doubleIlm.width()];
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.toArray(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = 10.0 * Math.log10(buffer[j]);
                }
            }
        }
    }
}
