package tfw.immutable.ilm.byteilm;

import java.util.Arrays;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class StridedByteIlmFromByteIlm {
    private StridedByteIlmFromByteIlm() {}

    public static StridedByteIlm create(final ByteIlm ilm, final byte[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedByteIlm(ilm, buffer);
    }

    private static class MyStridedByteIlm extends AbstractStridedByteIlm {
        private final ByteIlm ilm;
        private final byte[] buffer;

        public MyStridedByteIlm(final ByteIlm ilm, final byte[] buffer) {
            super(ilm.width(), ilm.height());

            this.ilm = ilm;
            this.buffer = buffer;
        }

        public final void toArrayImpl(
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            final byte[] b = Arrays.copyOf(buffer, colCount);

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
