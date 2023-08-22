package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleIlmScalarMultiply {
    private DoubleIlmScalarMultiply() {}

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
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.toArray(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = buffer[j] * scalar;
                }
            }
        }
    }
}
