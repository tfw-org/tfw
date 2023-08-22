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
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final int[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
