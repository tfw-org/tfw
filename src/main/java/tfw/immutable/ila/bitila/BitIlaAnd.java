package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaUtil.BitFunction;

public final class BitIlaAnd {
    private static final BitFunction AND_BIT_FUNCTION = new BitFunction() {
        @Override
        public void partialLong(
                long[] left, long leftOffsetInBits, long[] right, long rightOffsetInBits, long lengthInBits) {
            final long rightPartial = BitIlaUtil.getPartialLong(right, rightOffsetInBits, lengthInBits);
            final long leftPartial = BitIlaUtil.getPartialLong(left, leftOffsetInBits, lengthInBits);

            BitIlaUtil.setPartialLong(left, leftOffsetInBits, rightPartial & leftPartial, lengthInBits);
        }

        @Override
        public void forwardAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i < rightOffsetInLongs + lengthInLongs; i++, j++) {
                left[j] &= right[i];
            }
        }

        @Override
        public void forwardMisalignedFullLongs(
                long[] left,
                int leftOffsetInLongs,
                long[] right,
                int rightOffsetInLongs,
                int lengthInLongs,
                int rightOffsetMod64) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i < rightOffsetInLongs + lengthInLongs; i++, j++) {
                left[j] &= right[i] << rightOffsetMod64 | right[i + 1] >>> (Long.SIZE - rightOffsetMod64);
            }
        }

        @Override
        public void reverseAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i > rightOffsetInLongs - lengthInLongs; i--, j--) {
                left[j] &= right[i];
            }
        }

        @Override
        public void reverseMisalignedFullLongs(
                long[] left,
                int leftOffsetInLongs,
                long[] right,
                int rightOffsetInLongs,
                int lengthInLongs,
                int rightOffsetMod64) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i > rightOffsetInLongs - lengthInLongs; i--, j--) {
                left[j] &= right[i] << rightOffsetMod64 | right[i + 1] >>> (Long.SIZE - rightOffsetMod64);
            }
        }
    };

    private BitIlaAnd() {}

    public static void and(
            final long[] left,
            final long leftOffsetInBits,
            final long[] right,
            final long rightOffsetInBits,
            final long lengthInBits) {
        BitIlaUtil.performBitFunction(AND_BIT_FUNCTION, left, leftOffsetInBits, right, rightOffsetInBits, lengthInBits);
    }

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
            return Math.min(leftIla.lengthInBits(), rightIla.lengthInBits());
        }

        @Override
        protected void getImpl(long[] array, long arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            final long rightOffsetInBits = arrayOffsetInBits % Long.SIZE;
            final long[] rightArray = new long[(int) ((rightOffsetInBits + lengthInBits) / Long.SIZE + 1)];

            leftIla.get(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);
            rightIla.get(rightArray, rightOffsetInBits, ilaStartInBits, lengthInBits);

            and(array, arrayOffsetInBits, rightArray, rightOffsetInBits, lengthInBits);
        }
    }
}
