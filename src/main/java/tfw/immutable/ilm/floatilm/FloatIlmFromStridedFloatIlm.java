package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlmFromStridedFloatIlm {
    private FloatIlmFromStridedFloatIlm() {}

    public static FloatIlm create(final StridedFloatIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyFloatIlm(stridedIlm);
    }

    private static class MyFloatIlm extends AbstractFloatIlm {
        private final StridedFloatIlm stridedIlm;

        public MyFloatIlm(final StridedFloatIlm stridedIlm) {
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
        protected void toArrayImpl(
                final float[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
