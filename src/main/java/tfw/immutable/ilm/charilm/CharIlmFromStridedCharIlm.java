package tfw.immutable.ilm.charilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlmFromStridedCharIlm {
    private CharIlmFromStridedCharIlm() {}

    public static CharIlm create(final StridedCharIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyCharIlm(stridedIlm);
    }

    private static class MyCharIlm extends AbstractCharIlm {
        private final StridedCharIlm stridedIlm;

        public MyCharIlm(final StridedCharIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final char[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
