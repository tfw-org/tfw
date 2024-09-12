package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlmFromStridedDoubleIlm {
    private DoubleIlmFromStridedDoubleIlm() {}

    public static DoubleIlm create(final StridedDoubleIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new DoubleIlmImpl(stridedIlm);
    }

    private static class DoubleIlmImpl extends AbstractDoubleIlm {
        private final StridedDoubleIlm stridedIlm;

        private DoubleIlmImpl(final StridedDoubleIlm stridedIlm) {
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
                final double[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
