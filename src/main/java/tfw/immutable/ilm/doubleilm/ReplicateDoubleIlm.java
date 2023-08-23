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

        public MyDoubleIlm(DoubleIla doubleIla, long repetitions) {
            super(deleteMe(doubleIla), repetitions);

            this.doubleIla = doubleIla;
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            doubleIla.toArray(array, offset, colStart, colCount);

            for (int i = 0; i < rowCount; i++) {
                System.arraycopy(array, offset, array, offset + (colCount * i), colCount);
            }
        }

        private static long deleteMe(final DoubleIla doubleIla) {
            try {
                return doubleIla.length();
            } catch (IOException e) {
                return 0;
            }
        }
    }
}
