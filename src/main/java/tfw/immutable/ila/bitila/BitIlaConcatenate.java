package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;

public final class BitIlaConcatenate {
    private BitIlaConcatenate() {}

    public static BitIla create(final BitIla leftIla, final BitIla rightIla) {
        return new BitIlaImpl(leftIla, rightIla);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final BitIla leftIla;
        private final BitIla rightIla;

        public BitIlaImpl(final BitIla leftIla, final BitIla rightIla) {
            Argument.assertNotNull(leftIla, "leftIla");
            Argument.assertNotNull(rightIla, "rightIla");

            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(long[] array, int arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            if (ilaStartInBits + lengthInBits <= leftIla.length()) {
                leftIla.get(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
            } else if (ilaStartInBits >= leftIla.length()) {
                rightIla.get(array, arrayOffsetInBits, ilaStartInBits - leftIla.length(), lengthInBits);
            } else {
                final int leftAmount = (int) (leftIla.length() - ilaStartInBits);

                leftIla.get(array, arrayOffsetInBits, ilaStartInBits, leftAmount);
                rightIla.get(array, arrayOffsetInBits + leftAmount, 0, lengthInBits - leftAmount);
            }
        }
    }
}
