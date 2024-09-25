package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaUtil.BitFunction;

public final class BitIlaNot {
    private static final BitFunction NOT_BIT_FUNCTION = new BitFunction() {
        @Override
        public void partialLong(
                long[] left, long leftOffsetInBits, long[] right, long rightOffsetInBits, long lengthInBits) {
            final long rightPartial = BitIlaUtil.getPartialLong(right, rightOffsetInBits, lengthInBits);

            BitIlaUtil.setPartialLong(left, leftOffsetInBits, ~rightPartial, lengthInBits);
        }

        @Override
        public void forwardAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i < rightOffsetInLongs + lengthInLongs; i++, j++) {
                left[j] = ~right[i];
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
                left[j] = ~(right[i] << rightOffsetMod64 | right[i + 1] >>> Long.SIZE - rightOffsetMod64);
            }
        }

        @Override
        public void reverseAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i > rightOffsetInLongs - lengthInLongs; i--, j--) {
                left[j] = ~right[i];
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
                left[j] = ~(right[i] << rightOffsetMod64 | right[i + 1] >>> Long.SIZE - rightOffsetMod64);
            }
        }
    };

    private BitIlaNot() {}

    public static void not(final long[] array, final long offsetInBits, final long lengthInBits) {
        BitIlaUtil.performBitFunction(NOT_BIT_FUNCTION, array, offsetInBits, array, offsetInBits, lengthInBits);
    }

    public static BitIla create(final BitIla bitIla) {
        return new BitIlaImpl(bitIla);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final BitIla bitIla;

        public BitIlaImpl(final BitIla bitIla) {
            Argument.assertNotNull(bitIla, "bitIla");

            this.bitIla = bitIla;
        }

        @Override
        protected long lengthInBitsImpl() throws IOException {
            return bitIla.lengthInBits();
        }

        @Override
        protected void getImpl(long[] array, long arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            bitIla.get(array, arrayOffsetInBits, ilaStartInBits, lengthInBits);

            not(array, arrayOffsetInBits, lengthInBits);
        }
    }
}
