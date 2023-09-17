package tfw.immutable.ilm.longilm;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlmFromStridedLongIlm {
    private LongIlmFromStridedLongIlm() {}

    public static LongIlm create(final StridedLongIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new LongIlmImpl(stridedIlm);
    }

    private static class LongIlmImpl extends AbstractLongIlm {
        private final StridedLongIlm stridedIlm;

        private LongIlmImpl(final StridedLongIlm stridedIlm) {
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
                final long[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
