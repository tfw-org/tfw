package tfw.immutable.ilm.booleanilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedBooleanIlmFromBooleanIlm {
    private StridedBooleanIlmFromBooleanIlm() {}

    public static StridedBooleanIlm create(final BooleanIlm ilm, final boolean[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedBooleanIlmImpl(ilm, buffer);
    }

    private static class StridedBooleanIlmImpl extends AbstractStridedBooleanIlm {
        private final BooleanIlm ilm;
        private final boolean[] buffer;

        private StridedBooleanIlmImpl(final BooleanIlm ilm, final boolean[] buffer) {
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
                boolean[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final boolean[] b = Arrays.copyOf(buffer, colCount);

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
