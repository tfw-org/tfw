package tfw.immutable.ilm.intilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.doubleilm.DoubleIlm;

public class IntIlmFromCastDoubleIlm {
    private IntIlmFromCastDoubleIlm() {}

    public static IntIlm create(DoubleIlm doubleIlm) {
        Argument.assertNotNull(doubleIlm, "doubleIlm");

        return new MyIntIlm(doubleIlm);
    }

    private static class MyIntIlm extends AbstractIntIlm {
        private final DoubleIlm doubleIlm;

        private double[] buffer = new double[0];

        public MyIntIlm(DoubleIlm doubleIlm) {
            super(doubleIlm.width(), doubleIlm.height());

            this.doubleIlm = doubleIlm;
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            if (buffer.length < doubleIlm.width()) {
                buffer = new double[(int) doubleIlm.width()];
            }
            for (int i = 0; i < rowCount; i++) {
                doubleIlm.toArray(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = (int) buffer[j];
                }
            }
        }
    }
}
