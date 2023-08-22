package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlmFromStridedByteIlm {
    private ByteIlmFromStridedByteIlm() {}

    public static ByteIlm create(final StridedByteIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyByteIlm(stridedIlm);
    }

    private static class MyByteIlm extends AbstractByteIlm {
        private final StridedByteIlm stridedIlm;

        public MyByteIlm(final StridedByteIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final byte[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE