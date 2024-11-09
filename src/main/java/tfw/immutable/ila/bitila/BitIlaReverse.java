package tfw.immutable.ila.bitila;

import java.io.IOException;
import tfw.check.Argument;

public final class BitIlaReverse {
    private static final long REVERSE_MASK_32 = 0x00000000FFFFFFFFL;
    private static final long REVERSE_MASK_16 = 0x0000FFFF0000FFFFL;
    private static final long REVERSE_MASK_8 = 0x00FF00FF00FF00FFL;
    private static final long REVERSE_MASK_4 = 0x0F0F0F0F0F0F0F0FL;
    private static final long REVERSE_MASK_2 = 0x3333333333333333L;
    private static final long REVERSE_MASK_1 = 0x5555555555555555L;
    private static final long NOT_REVERSE_MASK_32 = ~REVERSE_MASK_32;
    private static final long NOT_REVERSE_MASK_16 = ~REVERSE_MASK_16;
    private static final long NOT_REVERSE_MASK_8 = ~REVERSE_MASK_8;
    private static final long NOT_REVERSE_MASK_4 = ~REVERSE_MASK_4;
    private static final long NOT_REVERSE_MASK_2 = ~REVERSE_MASK_2;
    private static final long NOT_REVERSE_MASK_1 = ~REVERSE_MASK_1;

    private BitIlaReverse() {}

    public static void reverse(final long[] array, final long offset, final long length) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");

        long leadPartial = 0;
        long leadPartialSize = 0;
        long offsetMod64 = offset % Long.SIZE;

        if (offsetMod64 != 0) {
            leadPartialSize = Long.SIZE - offsetMod64;

            if (leadPartialSize > length) {
                leadPartialSize = length;
            }

            leadPartial = BitIlaUtil.getPartialLong(array, offset, leadPartialSize);
        }

        long revLeadPartial = reverseLong(leadPartial);
        int longsOffset = (int) ((offset + leadPartialSize) / Long.SIZE);
        int numLongs = (int) ((length - leadPartialSize) / Long.SIZE);

        if (numLongs > 0) {
            reverseLongs(array, longsOffset, numLongs);
        }

        long trailPartialSize = (length - leadPartialSize) % Long.SIZE;
        long trailOffset = offset + leadPartialSize + numLongs * (long) Long.SIZE;
        long trailPartial = BitIlaUtil.getPartialLong(array, trailOffset, trailPartialSize);
        long revTrailFrag = reverseLong(trailPartial);

        if (numLongs > 0 && leadPartialSize != trailPartialSize) {
            BitIlaUtil.copy(
                    array,
                    longsOffset * (long) Long.SIZE + trailPartialSize - leadPartialSize,
                    array,
                    longsOffset * (long) Long.SIZE,
                    numLongs * (long) Long.SIZE);
        }

        BitIlaUtil.setLeftJustifiedPartialLong(array, offset, revTrailFrag, trailPartialSize);
        BitIlaUtil.setLeftJustifiedPartialLong(
                array, offset + length - leadPartialSize, revLeadPartial, leadPartialSize);
    }

    private static void reverseLongs(final long[] longs, int longsOffset, int numLongs) {
        int leftIndex = longsOffset;
        int rightIndex = longsOffset + numLongs - 1;

        while (leftIndex < rightIndex) {
            final long leftLongRev = reverseLong(longs[leftIndex]);
            final long rightLongRev = reverseLong(longs[rightIndex]);

            longs[leftIndex] = rightLongRev;
            longs[rightIndex] = leftLongRev;

            leftIndex++;
            rightIndex--;
        }

        if (leftIndex == rightIndex) {
            longs[leftIndex] = reverseLong(longs[leftIndex]);
        }
    }

    private static long reverseLong(final long value) {
        long reverseValue = (value & REVERSE_MASK_32) << 32 | (value & NOT_REVERSE_MASK_32) >>> 32;

        reverseValue = (reverseValue & REVERSE_MASK_16) << 16 | (reverseValue & NOT_REVERSE_MASK_16) >>> 16;
        reverseValue = (reverseValue & REVERSE_MASK_8) << 8 | (reverseValue & NOT_REVERSE_MASK_8) >>> 8;
        reverseValue = (reverseValue & REVERSE_MASK_4) << 4 | (reverseValue & NOT_REVERSE_MASK_4) >>> 4;
        reverseValue = (reverseValue & REVERSE_MASK_2) << 2 | (reverseValue & NOT_REVERSE_MASK_2) >>> 2;
        reverseValue = (reverseValue & REVERSE_MASK_1) << 1 | (reverseValue & NOT_REVERSE_MASK_1) >>> 1;

        return reverseValue;
    }

    public static BitIla create(final BitIla bitIla) {
        return new BitIlaImpl(bitIla);
    }

    private static class BitIlaImpl extends AbstractBitIla {
        private final BitIla bitIla;

        private BitIlaImpl(final BitIla bitIla) {
            Argument.assertNotNull(bitIla, "bitIla");

            this.bitIla = bitIla;
        }

        @Override
        protected void getImpl(long[] array, long arrayOffsetInBits, long ilaStartInBits, long lengthInBits)
                throws IOException {
            bitIla.get(array, arrayOffsetInBits, bitIla.lengthInBits() - ilaStartInBits - lengthInBits, lengthInBits);

            reverse(array, arrayOffsetInBits, lengthInBits);
        }

        @Override
        protected long lengthInBitsImpl() throws IOException {
            return bitIla.lengthInBits();
        }
    }
}
