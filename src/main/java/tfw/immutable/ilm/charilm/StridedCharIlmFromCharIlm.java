package tfw.immutable.ilm.charilm;

import java.io.IOException;
import java.util.Arrays;
import tfw.check.Argument;

public final class StridedCharIlmFromCharIlm {
    private StridedCharIlmFromCharIlm() {}

    public static StridedCharIlm create(final CharIlm ilm, final char[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedCharIlm(ilm, buffer);
    }

    private static class MyStridedCharIlm extends AbstractStridedCharIlm {
        private final CharIlm ilm;
        private final char[] buffer;

        public MyStridedCharIlm(final CharIlm ilm, final char[] buffer) {
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
                char[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws IOException {
            final char[] b = Arrays.copyOf(buffer, colCount);

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
