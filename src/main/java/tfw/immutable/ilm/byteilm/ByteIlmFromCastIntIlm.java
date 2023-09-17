package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ilm.intilm.IntIlm;

public class ByteIlmFromCastIntIlm {
    private ByteIlmFromCastIntIlm() {}

    public static ByteIlm create(IntIlm intIlm) {
        Argument.assertNotNull(intIlm, "intIlm");

        return new ByteIlmImpl(intIlm);
    }

    private static class ByteIlmImpl extends AbstractByteIlm {
        private final IntIlm intIlm;

        private int[] buffer = new int[0];

        private ByteIlmImpl(IntIlm intIlm) {
            this.intIlm = intIlm;
        }

        @Override
        protected long widthImpl() throws IOException {
            return intIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return intIlm.height();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            if (buffer.length < intIlm.width()) {
                buffer = new int[(int) intIlm.width()];
            }
            for (int i = 0; i < rowCount; i++) {
                intIlm.get(buffer, 0, rowStart + i, colStart, 1, colCount);

                for (int j = 0; j < colCount; j++) {
                    array[offset + (i * colCount) + j] = (byte) buffer[j];
                }
            }
        }
    }
}
