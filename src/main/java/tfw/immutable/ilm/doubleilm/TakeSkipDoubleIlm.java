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
        private final long take;
        private final long skip;

        public MyDoubleIlm(DoubleIla doubleIla, long take, long skip) {
            this.doubleIla = doubleIla;
            this.take = take;
            this.skip = skip;
        }

        @Override
        protected long widthImpl() throws IOException {
            return take;
        }

        @Override
        protected long heightImpl() throws IOException {
            return (doubleIla.length() - take) / skip + 1;
        }

        @Override
        protected void getImpl(double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            for (int i = 0; i < rowCount; i++) {
                doubleIla.get(array, offset + i * colCount, (rowStart + i) * skip + colStart, colCount);
            }
        }
    }
}
