package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedFloatIlmFromFloatIlm {
    private StridedFloatIlmFromFloatIlm() {}

    public static StridedFloatIlm create(final FloatIlm ilm, final float[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedFloatIlmImpl(ilm, buffer);
    }

    private static class StridedFloatIlmImpl extends AbstractStridedFloatIlm {
        private final FloatIlm ilm;
        private final float[] buffer;

        private StridedFloatIlmImpl(final FloatIlm ilm, final float[] buffer) {
            this.ilm = ilm;
            this.buffer = buffer;
        }

        @Override
        public long widthImpl() throws IOException {
            return ilm.width();
        }

        @Override
        public long heightImpl() throws IOException {
            return ilm.height();
        }

        @Override
        public final void getImpl(
                float[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final float[] b = Arrays.copyOf(buffer, colCount);

            for (int i = 0; i < rowCount; i++) {
                ilm.get(b, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] = b[j];
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
