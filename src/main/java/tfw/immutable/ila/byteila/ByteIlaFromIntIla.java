package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class ByteIlaFromIntIla {
    private ByteIlaFromIntIla() {}

    public static ByteIla create(IntIla intIla) {
        Argument.assertNotNull(intIla, "intIla");

        return new MyByteIla(intIla);
    }

    private static class MyByteIla extends AbstractByteIla {
        private IntIla intIla;

        MyByteIla(IntIla intIla) {
            super(intIla.length() * 4);

            this.intIla = intIla;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator iii =
                    new IntIlaIterator(IntIlaSegment.create(intIla, start / 4, intIla.length() - start / 4));
            int col = (int) (start % 4);

            for (int totalElements = 0; totalElements < length; ) {
                int value = iii.next();

                for (int c = col; c < 4 && totalElements < length; c++) {
                    array[offset + (totalElements++ * stride)] = (byte) ((value >>> (24 - (8 * c))) & 0xFF);
                }

                col = 0;
            }
        }
    }
}
