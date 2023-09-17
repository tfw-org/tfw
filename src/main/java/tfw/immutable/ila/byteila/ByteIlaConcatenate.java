package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaConcatenate {
    private ByteIlaConcatenate() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        /*
          // this efficiency step would help out in a number
          // of situations, but could be confusing when the
          // immutable proxy getParameters() is called and you
          // don't see your concatenation!
        if(leftIla.length() == 0)
            return rightIla;
        if(rightIla.length() == 0)
            return leftIla;
        */
        return new ByteIlaImpl(leftIla, rightIla);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla leftIla;
        private final ByteIla rightIla;

        private ByteIlaImpl(ByteIla leftIla, ByteIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            final long leftIlaLength = leftIla.length();

            if (start + length <= leftIlaLength) {
                leftIla.get(array, offset, start, length);
            } else if (start >= leftIlaLength) {
                rightIla.get(array, offset, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.get(array, offset, start, leftAmount);
                rightIla.get(array, offset + leftAmount, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
