package tfw.immutable.ilm.doubleilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedDoubleIlmFromDoubleIlm {
    private StridedDoubleIlmFromDoubleIlm() {}

    public static StridedDoubleIlm create(final DoubleIlm ilm, final double[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedDoubleIlmImpl(ilm, buffer);
    }

    private static class StridedDoubleIlmImpl extends AbstractStridedDoubleIlm {
        private final DoubleIlm ilm;
        private final double[] buffer;

        private StridedDoubleIlmImpl(final DoubleIlm ilm, final double[] buffer) {
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
                double[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final double[] b = Arrays.copyOf(buffer, colCount);

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
