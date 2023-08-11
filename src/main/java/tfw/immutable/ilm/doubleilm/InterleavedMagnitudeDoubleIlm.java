package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class InterleavedMagnitudeDoubleIlm {
    private InterleavedMagnitudeDoubleIlm() {}

    public static DoubleIlm create(DoubleIlm doubleIlm) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");
        Argument.assertEquals(doubleIlm.width() % 2, 0, "doubleIlm.width()", "0");

        return new MyDoubleIlm(doubleIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final double[] buffer;

        public MyDoubleIlm(DoubleIlm doubleIlm) {
            super(doubleIlm.width() / 2, doubleIlm.height());

            this.doubleIlm = doubleIlm;
            this.buffer = new double[(int) doubleIlm.width()];
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.toArray(buffer, 0, rowStart + i, colStart * 2, 1, colCount * 2);

                for (int j = 0; j < colCount; j++) {
                    double real = buffer[j * 2];
                    double imag = buffer[j * 2 + 1];

                    array[offset + (i * colCount) + j] = real * real + imag * imag;
                }
            }
        }
    }
}
