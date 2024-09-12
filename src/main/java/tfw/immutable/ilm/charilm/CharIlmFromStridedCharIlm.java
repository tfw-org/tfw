package tfw.immutable.ilm.charilm;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlmFromStridedCharIlm {
    private CharIlmFromStridedCharIlm() {}

    public static CharIlm create(final StridedCharIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new CharIlmImpl(stridedIlm);
    }

    private static class CharIlmImpl extends AbstractCharIlm {
        private final StridedCharIlm stridedIlm;

        private CharIlmImpl(final StridedCharIlm stridedIlm) {
            this.stridedIlm = stridedIlm;
        }

        @Override
        protected long widthImpl() throws IOException {
            return stridedIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return stridedIlm.height();
        }

        @Override
        protected void getImpl(
                final char[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
