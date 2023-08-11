package tfw.immutable.ilm.booleanilm;

import java.util.Arrays;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class StridedBooleanIlmFromBooleanIlm {
    private StridedBooleanIlmFromBooleanIlm() {}

    public static StridedBooleanIlm create(final BooleanIlm ilm, final boolean[] buffer) {
        Argument.assertNotNull(ilm, "ilm");
        Argument.assertNotNull(buffer, "buffer");

        return new MyStridedBooleanIlm(ilm, buffer);
    }

    private static class MyStridedBooleanIlm extends AbstractStridedBooleanIlm {
        private final BooleanIlm ilm;
        private final boolean[] buffer;

        public MyStridedBooleanIlm(final BooleanIlm ilm, final boolean[] buffer) {
            super(ilm.width(), ilm.height());

            this.ilm = ilm;
            this.buffer = buffer;
        }

        public final void toArrayImpl(
                boolean[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            final boolean[] b = Arrays.copyOf(buffer, colCount);

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
