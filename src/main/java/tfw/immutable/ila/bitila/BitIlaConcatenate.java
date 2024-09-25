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
        protected long lengthInBitsImpl() throws IOException {
            return leftIla.lengthInBits() + rightIla.lengthInBits();
        }

        @Override
        protected void getImpl(long[] array, long arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            if (ilaStartInBits + lengthInBits <= leftIla.lengthInBits()) {
                leftIla.get(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
            } else if (ilaStartInBits >= leftIla.lengthInBits()) {
                rightIla.get(array, arrayOffsetInBits, ilaStartInBits - leftIla.lengthInBits(), lengthInBits);
            } else {
                final int leftAmount = (int) (leftIla.lengthInBits() - ilaStartInBits);

                leftIla.get(array, arrayOffsetInBits, ilaStartInBits, leftAmount);
                rightIla.get(array, arrayOffsetInBits + leftAmount, 0, lengthInBits - leftAmount);
            }
        }
    }
}
