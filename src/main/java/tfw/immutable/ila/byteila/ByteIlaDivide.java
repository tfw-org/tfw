package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaDivide {
    private ByteIlaDivide() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(leftIla, rightIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla leftIla;
        private final ByteIla rightIla;
        private final int bufferSize;

        MyByteIla(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long ilaStart, int length)
                throws DataInvalidException {
            ByteIlaIterator li =
                    new ByteIlaIterator(ByteIlaSegment.create(leftIla, ilaStart, length), new byte[bufferSize]);
            ByteIlaIterator ri =
                    new ByteIlaIterator(ByteIlaSegment.create(rightIla, ilaStart, length), new byte[bufferSize]);

            for (int ii = offset; li.hasNext(); ii += stride) {
                array[ii] = (byte) (li.next() / ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
