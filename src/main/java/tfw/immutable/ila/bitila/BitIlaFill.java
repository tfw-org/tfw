package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.bitila.BitIlaUtil.BitFunction;

public final class BitIlaFill {
    private static final BitFunction FILL_BIT_FUNCTION = new BitFunction() {
        @Override
        public void partialLong(
                long[] left, long leftOffsetInBits, long[] right, long rightOffsetInBits, long lengthInBits) {
            final long value = right[0] == 0 ? 0 : -1;

            BitIlaUtil.setPartialLong(left, leftOffsetInBits, value, lengthInBits);
        }

        @Override
        public void forwardAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            final long value = right[0] == 0 ? 0 : -1;

            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i < rightOffsetInLongs + lengthInLongs; i++, j++) {
                left[j] = value;
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
            final long value = right[0] == 0 ? 0 : -1;

            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i < rightOffsetInLongs + lengthInLongs; i++, j++) {
                left[j] = value;
            }
        }

        @Override
        public void reverseAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            final long value = right[0] == 0 ? 0 : -1;

            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i > rightOffsetInLongs - lengthInLongs; i--, j--) {
                left[j] = value;
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
            final long value = right[0] == 0 ? 0 : -1;

            for (int i = rightOffsetInLongs, j = leftOffsetInLongs; i > rightOffsetInLongs - lengthInLongs; i--, j--) {
                left[j] = value;
            }
        }
    };

    private static final long[] ZERO = new long[] {0L};
    private static final long[] ONE = new long[] {1L};

    private BitIlaFill() {}

    public static void fill(final long[] array, final long offsetInBits, final long lengthInBits, final boolean value) {
        BitIlaUtil.performBitFunction(FILL_BIT_FUNCTION, array, offsetInBits, value ? ONE : ZERO, 0, lengthInBits);
    }

    public static BitIla create(final boolean value, final long lengthInBits) {
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");

        return new BitIlaImpl(value, lengthInBits);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final boolean value;
        private final long lengthInBits;

        public BitIlaImpl(final boolean value, final long lengthInBits) {
            this.value = value;
            this.lengthInBits = lengthInBits;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return lengthInBits;
        }

        @Override
        protected void getImpl(long[] array, int arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            fill(array, arrayOffsetInBits, lengthInBits, value);
        }
    }
}
