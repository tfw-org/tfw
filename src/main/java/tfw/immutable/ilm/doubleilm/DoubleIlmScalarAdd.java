package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class DoubleIlmScalarAdd {
    private DoubleIlmScalarAdd() {}

    public static DoubleIlm create(DoubleIlm doubleIlm, double scalar) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");

        return new MyDoubleIlm(doubleIlm, scalar);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIlm doubleIlm;
        private final double[] buffer;
        private final double scalar;

        public MyDoubleIlm(DoubleIlm doubleIlm, double scalar) {
            super(doubleIlm.width(), doubleIlm.height());

            this.doubleIlm = doubleIlm;
            this.buffer = new double[(int) doubleIlm.width()];
            this.scalar = scalar;
        }

        @Override
        protected void toArrayImpl(
                double[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.toArray(buffer, 0, buffer.length, 1, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] = buffer[j] + scalar;
                }
            }
        }
    }
}
