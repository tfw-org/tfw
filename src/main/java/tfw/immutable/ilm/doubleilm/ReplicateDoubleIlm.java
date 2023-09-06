package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;

public class ReplicateDoubleIlm {
    private ReplicateDoubleIlm() {}

    public static DoubleIlm create(DoubleIla doubleIla, long repetitions) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(repetitions, 1, "repetitions");

        return new MyDoubleIlm(doubleIla, repetitions);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIla doubleIla;
        private final long repetitions;

        public MyDoubleIlm(DoubleIla doubleIla, long repetitions) {
            this.doubleIla = doubleIla;
            this.repetitions = repetitions;
        }

        @Override
        protected long widthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected long heightImpl() throws IOException {
            return repetitions;
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            doubleIla.toArray(array, offset, colStart, colCount);

            for (int i = 0; i < rowCount; i++) {
                System.arraycopy(array, offset, array, offset + (colCount * i), colCount);
            }
        }
    }
}
