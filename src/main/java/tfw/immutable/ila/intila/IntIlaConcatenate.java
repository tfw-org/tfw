package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaConcatenate {
    private IntIlaConcatenate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla leftIla, IntIla rightIla) {
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
        return new IntIlaImpl(leftIla, rightIla);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla leftIla;
        private final IntIla rightIla;

        private IntIlaImpl(IntIla leftIla, IntIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            final long leftIlaLength = leftIla.length();
            final long leftIlaLastIndex = leftIlaLength - 1;

            if (start + length <= leftIlaLastIndex) {
                leftIla.get(array, offset, start, length);
            } else if (start > leftIlaLastIndex) {
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
