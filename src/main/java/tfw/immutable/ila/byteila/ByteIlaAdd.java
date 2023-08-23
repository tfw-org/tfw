package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaAdd {
    private ByteIlaAdd() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla, int bufferSize) throws IOException {
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
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void toArrayImpl(byte[] array, int offset, long ilaStart, int length) throws IOException {
            ByteIlaIterator li =
                    new ByteIlaIterator(ByteIlaSegment.create(leftIla, ilaStart, length), new byte[bufferSize]);
            ByteIlaIterator ri =
                    new ByteIlaIterator(ByteIlaSegment.create(rightIla, ilaStart, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (byte) (li.next() + ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
