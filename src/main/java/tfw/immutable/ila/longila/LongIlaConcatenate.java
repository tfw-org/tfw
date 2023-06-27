package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaConcatenate {
    private LongIlaConcatenate() {
        // non-instantiable class
    }

    public static LongIla create(LongIla leftIla, LongIla rightIla) {
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
        return new MyLongIla(leftIla, rightIla);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla leftIla;
        private final LongIla rightIla;
        private final long leftIlaLength;

        MyLongIla(LongIla leftIla, LongIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
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
