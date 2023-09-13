package tfw.immutable.ilm.intilm;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlmFromStridedIntIlm {
    private IntIlmFromStridedIntIlm() {}

    public static IntIlm create(final StridedIntIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyIntIlm(stridedIlm);
    }

    private static class MyIntIlm extends AbstractIntIlm {
        private final StridedIntIlm stridedIlm;

        public MyIntIlm(final StridedIntIlm stridedIlm) {
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
                final int[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
