package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlmFromStridedBooleanIlm {
    private BooleanIlmFromStridedBooleanIlm() {}

    public static BooleanIlm create(final StridedBooleanIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyBooleanIlm(stridedIlm);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm {
        private final StridedBooleanIlm stridedIlm;

        public MyBooleanIlm(final StridedBooleanIlm stridedIlm) {
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
                final boolean[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
