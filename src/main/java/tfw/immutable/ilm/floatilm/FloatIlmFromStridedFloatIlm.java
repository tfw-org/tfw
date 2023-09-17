package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlmFromStridedFloatIlm {
    private FloatIlmFromStridedFloatIlm() {}

    public static FloatIlm create(final StridedFloatIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new FloatIlmImpl(stridedIlm);
    }

    private static class FloatIlmImpl extends AbstractFloatIlm {
        private final StridedFloatIlm stridedIlm;

        private FloatIlmImpl(final StridedFloatIlm stridedIlm) {
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
                final float[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
