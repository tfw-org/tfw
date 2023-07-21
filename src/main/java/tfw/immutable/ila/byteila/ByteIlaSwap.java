package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

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
            super(byteIla.length() - (byteIla.length() % bytesToSwap));

            this.byteIla = byteIla;
            this.bytesToSwap = bytesToSwap;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
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

                array[offset + (l * stride)] = bytes[bs];
            }
        }
    }
}
