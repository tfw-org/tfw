package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedByteIlmFromByteIlm {
    private StridedByteIlmFromByteIlm() {}

    public static StridedByteIlm create(final ByteIlm ilm, final byte[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new StridedByteIlmImpl(ilm, buffer);
    }

    private static class StridedByteIlmImpl extends AbstractStridedByteIlm {
        private final ByteIlm ilm;
        private final byte[] buffer;

        private StridedByteIlmImpl(final ByteIlm ilm, final byte[] buffer) {
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
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final byte[] b = Arrays.copyOf(buffer, colCount);

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
