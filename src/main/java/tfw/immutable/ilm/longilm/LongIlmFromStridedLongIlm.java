package tfw.immutable.ilm.longilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlmFromStridedLongIlm {
    private LongIlmFromStridedLongIlm() {}

    public static LongIlm create(final StridedLongIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyLongIlm(stridedIlm);
    }

    private static class MyLongIlm extends AbstractLongIlm {
        private final StridedLongIlm stridedIlm;

        public MyLongIlm(final StridedLongIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final long[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
