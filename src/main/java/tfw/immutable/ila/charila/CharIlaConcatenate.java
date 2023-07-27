package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlaConcatenate {
    private CharIlaConcatenate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla leftIla, CharIla rightIla) {
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
        return new MyCharIla(leftIla, rightIla);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla leftIla;
        private final CharIla rightIla;
        private final long leftIlaLength;

        MyCharIla(CharIla leftIla, CharIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(char[] array, int offset, long start, int length) throws DataInvalidException {
            if (start + length <= leftIlaLength) {
                leftIla.toArray(array, offset, start, length);
            } else if (start >= leftIlaLength) {
                rightIla.toArray(array, offset, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
