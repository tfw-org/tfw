package tfw.immutable.ilm.shortilm;

import java.util.Arrays;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

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
            super(ilm.width(), ilm.height());

            this.ilm = ilm;
            this.buffer = buffer;
        }

        public final void toArrayImpl(
                short[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            final short[] b = Arrays.copyOf(buffer, colCount);

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
