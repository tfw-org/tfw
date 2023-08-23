package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public class ByteIlaSwap {
    private ByteIlaSwap() {}

    public static ByteIla create(final ByteIla byteIla, final int bytesToSwap, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bytesToSwap, 2, "bytesToSwap");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(byteIla, bytesToSwap, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla byteIla;
        private final int bytesToSwap;
        private final int bufferSize;

        MyByteIla(final ByteIla byteIla, final int bytesToSwap, final int bufferSize) {
            this.byteIla = byteIla;
            this.bytesToSwap = bytesToSwap;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length() - (byteIla.length() % bytesToSwap);
        }

        @Override
        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws IOException {
            long end = start + length - 1;
            long blockStart = start - (start % bytesToSwap);
            long blockEnd = end + bytesToSwap - (end % bytesToSwap) - 1;
            int blockLength = (int) (blockEnd - blockStart + 1);
            ByteIlaIterator bii =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, blockStart, blockLength), new byte[bufferSize]);
            byte[] bytes = new byte[bytesToSwap];

            for (int j = bytesToSwap - 1; j >= 0; j--) {
                bytes[j] = bii.next();
            }
            int bs = (int) (start % bytesToSwap);

            for (int l = 0; l < length; l++, bs++) {
                if (bs == bytesToSwap) {
                    for (int j = bytesToSwap - 1; j >= 0; j--) {
                        bytes[j] = bii.next();
                    }
                    bs = 0;
                }

                array[offset + l] = bytes[bs];
            }
        }
    }
}
