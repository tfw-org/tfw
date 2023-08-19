package tfw.immutable.ilm.doubleilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlmFromStridedDoubleIlm {
    private DoubleIlmFromStridedDoubleIlm() {}

    public static DoubleIlm create(final StridedDoubleIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyDoubleIlm(stridedIlm);
    }

    private static class MyDoubleIlm extends AbstractDoubleIlm {
        private final StridedDoubleIlm stridedIlm;

        public MyDoubleIlm(final StridedDoubleIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final double[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
