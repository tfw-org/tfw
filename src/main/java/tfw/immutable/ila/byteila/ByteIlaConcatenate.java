package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
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
        return new MyByteIla(leftIla, rightIla);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla leftIla;
        private final ByteIla rightIla;
        private final long leftIlaLength;

        MyByteIla(ByteIla leftIla, ByteIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            if (start + length <= leftIlaLength) {
                leftIla.toArray(array, offset, stride, start, length);
            } else if (start >= leftIlaLength) {
                rightIla.toArray(array, offset, stride, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, stride, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount * stride, stride, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
