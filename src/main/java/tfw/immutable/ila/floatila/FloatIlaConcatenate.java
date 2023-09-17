package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaConcatenate {
    private FloatIlaConcatenate() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla) {
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
        return new FloatIlaImpl(leftIla, rightIla);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla leftIla;
        private final FloatIla rightIla;

        private FloatIlaImpl(FloatIla leftIla, FloatIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
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
