package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaDivide {
    private ByteIlaDivide() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
        return new ByteIlaImpl(leftIla, rightIla, bufferSize);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla leftIla;
        private final ByteIla rightIla;
        private final int bufferSize;

        private ByteIlaImpl(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
            Argument.assertNotNull(leftIla, "leftIla");
            Argument.assertNotNull(rightIla, "rightIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");
            try {
                Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length!", e);
            }

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long ilaStart, int length) throws IOException {
            ByteIlaIterator li =
                    new ByteIlaIterator(ByteIlaSegment.create(leftIla, ilaStart, length), new byte[bufferSize]);
            ByteIlaIterator ri =
                    new ByteIlaIterator(ByteIlaSegment.create(rightIla, ilaStart, length), new byte[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = (byte) (li.next() / ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
