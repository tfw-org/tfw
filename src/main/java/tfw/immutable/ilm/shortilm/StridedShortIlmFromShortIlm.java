package tfw.immutable.ilm.shortilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedShortIlmFromShortIlm {
    private StridedShortIlmFromShortIlm() {}

    public static StridedShortIlm create(final ShortIlm ilm, final short[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedShortIlm(ilm, buffer);
    }

    private static class MyStridedShortIlm extends AbstractStridedShortIlm {
        private final ShortIlm ilm;
        private final short[] buffer;

        public MyStridedShortIlm(final ShortIlm ilm, final short[] buffer) {
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
                short[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final short[] b = Arrays.copyOf(buffer, colCount);

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
