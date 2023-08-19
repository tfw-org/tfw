package tfw.immutable.ilm.floatilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class FloatIlmFromStridedFloatIlm {
    private FloatIlmFromStridedFloatIlm() {}

    public static FloatIlm create(final StridedFloatIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyFloatIlm(stridedIlm);
    }

    private static class MyFloatIlm extends AbstractFloatIlm {
        private final StridedFloatIlm stridedIlm;

        public MyFloatIlm(final StridedFloatIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final float[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
