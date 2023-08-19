package tfw.immutable.ilm.shortilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlmFromStridedShortIlm {
    private ShortIlmFromStridedShortIlm() {}

    public static ShortIlm create(final StridedShortIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyShortIlm(stridedIlm);
    }

    private static class MyShortIlm extends AbstractShortIlm {
        private final StridedShortIlm stridedIlm;

        public MyShortIlm(final StridedShortIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final short[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
