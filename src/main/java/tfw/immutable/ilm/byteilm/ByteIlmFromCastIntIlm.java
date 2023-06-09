package tfw.immutable.ilm.byteilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ilm.intilm.IntIlm;

public class ByteIlmFromCastIntIlm {
    private ByteIlmFromCastIntIlm() {}

    public static ByteIlm create(IntIlm intIlm) {
        Argument.assertNotNull(intIlm, "intIlm");

        return new MyByteIlm(intIlm);
    }

    private static class MyByteIlm extends AbstractByteIlm {
        private final IntIlm intIlm;

        private int[] buffer = new int[0];

        public MyByteIlm(IntIlm intIlm) {
            super(intIlm.width(), intIlm.height());

            this.intIlm = intIlm;
        }

        @Override
        protected void toArrayImpl(
                byte[] array,
                int offset,
                int rowStride,
                int colStride,
                long rowStart,
                long colStart,
                int rowCount,
                int colCount)
                throws DataInvalidException {
            if (buffer.length < intIlm.width()) {
                buffer = new int[(int) intIlm.width()];
            }
            for (int i = 0; i < rowCount; i++) {
                intIlm.toArray(buffer, 0, buffer.length, 1, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * rowStride) + (j * colStride)] = (byte) buffer[j];
                }
            }
        }
    }
}
