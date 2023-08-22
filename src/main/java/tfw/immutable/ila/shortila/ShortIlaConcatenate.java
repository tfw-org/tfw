package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaConcatenate {
    private ShortIlaConcatenate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla leftIla, ShortIla rightIla) {
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
        return new MyShortIla(leftIla, rightIla);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla leftIla;
        private final ShortIla rightIla;
        private final long leftIlaLength;

        MyShortIla(ShortIla leftIla, ShortIla rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
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
