package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;

public class TakeSkipDoubleIlm {
    private TakeSkipDoubleIlm() {}

    public static DoubleIlm create(DoubleIla doubleIla, long take, long skip) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(take, 1, "take");
        Argument.assertNotLessThan(skip, 1, "skip");

        return new MyDoubleIlm(doubleIla, take, skip);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final DoubleIla doubleIla;
        private final long skip;

        public MyDoubleIlm(DoubleIla doubleIla, long take, long skip) {
            super(take, (deleteMe(doubleIla) - take) / skip + 1);

            this.doubleIla = doubleIla;
            this.skip = skip;
        }

        @Override
        protected void toArrayImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                doubleIla.toArray(array, offset + i * colCount, (rowStart + i) * skip + colStart, colCount);
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
