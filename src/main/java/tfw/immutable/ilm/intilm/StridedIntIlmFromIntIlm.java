package tfw.immutable.ilm.intilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedIntIlmFromIntIlm {
    private StridedIntIlmFromIntIlm() {}

    public static StridedIntIlm create(final IntIlm ilm, final int[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedIntIlm(ilm, buffer);
    }

    private static class MyStridedIntIlm extends AbstractStridedIntIlm {
        private final IntIlm ilm;
        private final int[] buffer;

        public MyStridedIntIlm(final IntIlm ilm, final int[] buffer) {
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
        public final void toArrayImpl(
                int[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final int[] b = Arrays.copyOf(buffer, colCount);

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
