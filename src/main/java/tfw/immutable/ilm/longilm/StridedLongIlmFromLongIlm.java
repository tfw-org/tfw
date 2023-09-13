package tfw.immutable.ilm.longilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedLongIlmFromLongIlm {
    private StridedLongIlmFromLongIlm() {}

    public static StridedLongIlm create(final LongIlm ilm, final long[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedLongIlm(ilm, buffer);
    }

    private static class MyStridedLongIlm extends AbstractStridedLongIlm {
        private final LongIlm ilm;
        private final long[] buffer;

        public MyStridedLongIlm(final LongIlm ilm, final long[] buffer) {
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
                long[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final long[] b = Arrays.copyOf(buffer, colCount);

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
