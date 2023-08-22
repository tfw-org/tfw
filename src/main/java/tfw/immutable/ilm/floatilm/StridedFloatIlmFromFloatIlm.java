package tfw.immutable.ilm.floatilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedFloatIlmFromFloatIlm {
    private StridedFloatIlmFromFloatIlm() {}

    public static StridedFloatIlm create(final FloatIlm ilm, final float[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedFloatIlm(ilm, buffer);
    }

    private static class MyStridedFloatIlm extends AbstractStridedFloatIlm {
        private final FloatIlm ilm;
        private final float[] buffer;

        public MyStridedFloatIlm(final FloatIlm ilm, final float[] buffer) {
            super(ilm.width(), ilm.height());

            this.ilm = ilm;
            this.buffer = buffer;
        }

        public final void toArrayImpl(
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
                ilm.toArray(b, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] = b[j];
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
