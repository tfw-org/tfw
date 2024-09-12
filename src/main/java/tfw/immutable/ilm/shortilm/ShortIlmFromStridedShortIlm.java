package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlmFromStridedShortIlm {
    private ShortIlmFromStridedShortIlm() {}

    public static ShortIlm create(final StridedShortIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new ShortIlmImpl(stridedIlm);
    }

    private static class ShortIlmImpl extends AbstractShortIlm {
        private final StridedShortIlm stridedIlm;

        private ShortIlmImpl(final StridedShortIlm stridedIlm) {
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
                final short[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
