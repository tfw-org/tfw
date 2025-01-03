package tfw.immutable.ila.bitila;

import java.math.BigInteger;
import tfw.check.Argument;

public final class BitIlaUtil {
    private static final BitFunction COPY_BIT_FUNCTION = new BitFunction() {
        @Override
        public void partialLong(
                long[] left, long leftOffsetInBits, long[] right, long rightOffsetInBits, long lengthInBits) {
            final long partial = getPartialLong(right, rightOffsetInBits, lengthInBits);

            setPartialLong(left, leftOffsetInBits, partial, lengthInBits);
        }

        @Override
        public void forwardAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            System.arraycopy(right, rightOffsetInLongs, left, leftOffsetInLongs, lengthInLongs);
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
                left[j] = right[i] << rightOffsetMod64 | right[i + 1] >>> (Long.SIZE - rightOffsetMod64);
            }
        }

        @Override
        public void reverseAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs) {
            System.arraycopy(
                    right,
                    rightOffsetInLongs - (lengthInLongs - 1),
                    left,
                    leftOffsetInLongs - (lengthInLongs - 1),
                    lengthInLongs);
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
                left[j] = right[i] << rightOffsetMod64 | right[i + 1] >>> (Long.SIZE - rightOffsetMod64);
            }
        }
    };

    public interface BitFunction {
        void partialLong(long[] left, long leftOffsetInBits, long[] right, long rightOffsetInBits, long lengthInBits);

        void forwardAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs);

        void forwardMisalignedFullLongs(
                long[] left,
                int leftOffsetInLongs,
                long[] right,
                int rightOffsetInLongs,
                int lengthInLongs,
                int rightOffsetMod64);

        void reverseAlignedFullLongs(
                long[] left, int leftOffsetInLongs, long[] right, int rightOffsetInLongs, int lengthInLongs);

        void reverseMisalignedFullLongs(
                long[] left,
                int leftOffsetInLongs,
                long[] right,
                int rightOffsetInLongs,
                int lengthInLongs,
                int rightOffsetMod64);
    }

    private BitIlaUtil() {}

    public static void performBitFunction(
            final BitFunction bitFunction,
            final long[] left,
            final long leftOffsetInBits,
            final long[] right,
            final long rightOffsetInBits,
            final long lengthInBits) {
        Argument.assertNotNull(bitFunction, "bitFunction");
        Argument.assertNotNull(left, "left");
        Argument.assertNotNull(right, "right");
        Argument.assertNotLessThan(leftOffsetInBits, 0, "leftOffsetInBits");
        Argument.assertNotLessThan(rightOffsetInBits, 0, "rightOffsetInBits");
        Argument.assertNotLessThan(lengthInBits, 1, "lengthInBits");
        Argument.assertNotGreaterThan(
                leftOffsetInBits + lengthInBits,
                left.length * (long) Long.SIZE,
                "leftOffsetInBits + lengthInBits",
                "left.length * Long.SIZE");
        Argument.assertNotGreaterThan(
                rightOffsetInBits + lengthInBits,
                right.length * (long) Long.SIZE,
                "rightOffsetInBits + lengthInBits",
                "right.length * Long.SIZE");

        final BigInteger leftLastIndex = BigInteger.valueOf(leftOffsetInBits).add(BigInteger.valueOf(left.length));
        final BigInteger actualLeftLastIndex = BigInteger.valueOf(left.length * (long) Long.SIZE);
        final BigInteger rightLastIndex = BigInteger.valueOf(rightOffsetInBits).add(BigInteger.valueOf(right.length));
        final BigInteger actualRightLastIndex = BigInteger.valueOf(right.length * (long) Long.SIZE);

        Argument.assertNotGreaterThan(leftLastIndex.compareTo(actualLeftLastIndex), 0, "leftOffsetInBits+lengthInBits");
        Argument.assertNotGreaterThan(
                rightLastIndex.compareTo(actualRightLastIndex), 0, "rightOffsetInBits+lengthInBits");

        if (lengthInBits < Long.SIZE) {
            bitFunction.partialLong(left, leftOffsetInBits, right, rightOffsetInBits, lengthInBits);
        } else if (right == left
                && rightOffsetInBits < leftOffsetInBits
                && rightOffsetInBits + lengthInBits > leftOffsetInBits) {
            reverseBitFunction(bitFunction, left, leftOffsetInBits, right, rightOffsetInBits, lengthInBits);
        } else {
            forwardBitFunction(bitFunction, left, leftOffsetInBits, right, rightOffsetInBits, lengthInBits);
        }
    }

    private static void reverseBitFunction(
            BitFunction bitFunction,
            long[] left,
            long leftOffsetInBits,
            long[] right,
            long rightOffsetInBits,
            long lengthInBits) {
        rightOffsetInBits += lengthInBits;
        leftOffsetInBits += lengthInBits;

        long rightOffsetMod64 = rightOffsetInBits % Long.SIZE;
        long leftOffsetMod64 = leftOffsetInBits % Long.SIZE;

        if (leftOffsetMod64 != 0) {
            final long trailingPartialSize = Math.min(leftOffsetMod64, lengthInBits);

            lengthInBits -= trailingPartialSize;
            leftOffsetInBits -= trailingPartialSize;
            rightOffsetInBits -= trailingPartialSize;
            rightOffsetMod64 = rightOffsetInBits % Long.SIZE;

            bitFunction.partialLong(left, leftOffsetInBits, right, rightOffsetInBits, trailingPartialSize);
        }

        if (lengthInBits > Long.SIZE - 1) {
            final int lengthInLongs = (int) (lengthInBits / Long.SIZE);
            final int leftOffsetInLongs = (int) ((leftOffsetInBits - Long.SIZE) / Long.SIZE);
            final int rightOffsetInLongs = (int) ((rightOffsetInBits - Long.SIZE) / Long.SIZE);

            if (rightOffsetMod64 == 0) {
                bitFunction.reverseAlignedFullLongs(left, leftOffsetInLongs, right, rightOffsetInLongs, lengthInLongs);
            } else {
                bitFunction.reverseMisalignedFullLongs(
                        left, leftOffsetInLongs, right, rightOffsetInLongs, lengthInLongs, (int) rightOffsetMod64);
            }

            rightOffsetInBits -= lengthInLongs * (long) Long.SIZE;
            leftOffsetInBits -= lengthInLongs * (long) Long.SIZE;
        }

        final long leadingPartialSize = lengthInBits % Long.SIZE;

        rightOffsetInBits -= leadingPartialSize;
        leftOffsetInBits -= leadingPartialSize;

        bitFunction.partialLong(left, leftOffsetInBits, right, rightOffsetInBits, leadingPartialSize);
    }

    private static void forwardBitFunction(
            BitFunction bitFunction,
            long[] left,
            long leftOffsetInBits,
            long[] right,
            long rightOffsetInBits,
            long lengthInBits) {
        long rightOffsetMod64 = rightOffsetInBits % Long.SIZE;
        long leftOffsetMod64 = leftOffsetInBits % Long.SIZE;

        if (leftOffsetMod64 != 0) {
            final long leadingPartialSize = Math.min(Long.SIZE - leftOffsetMod64, lengthInBits);

            bitFunction.partialLong(left, leftOffsetInBits, right, rightOffsetInBits, leadingPartialSize);

            lengthInBits -= leadingPartialSize;
            leftOffsetInBits += leadingPartialSize;
            rightOffsetInBits += leadingPartialSize;
            rightOffsetMod64 = rightOffsetInBits % Long.SIZE;
        }

        final int lengthInLongs = (int) (lengthInBits / Long.SIZE);
        final int leftOffsetInLongs = (int) (leftOffsetInBits / Long.SIZE);
        final int rightOffsetInLongs = (int) (rightOffsetInBits / Long.SIZE);

        if (rightOffsetMod64 == 0) {
            bitFunction.forwardAlignedFullLongs(left, leftOffsetInLongs, right, rightOffsetInLongs, lengthInLongs);
        } else {
            bitFunction.forwardMisalignedFullLongs(
                    left, leftOffsetInLongs, right, rightOffsetInLongs, lengthInLongs, (int) rightOffsetMod64);
        }

        rightOffsetInBits += lengthInLongs * (long) Long.SIZE;
        leftOffsetInBits += lengthInLongs * (long) Long.SIZE;

        final long trailingPartialSize = lengthInBits % Long.SIZE;

        bitFunction.partialLong(left, leftOffsetInBits, right, rightOffsetInBits, trailingPartialSize);
    }

    public static long getPartialLong(final long[] array, final long offsetInBits, final long lengthInBits) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offsetInBits, 0, "offsetInBits");
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");
        Argument.assertNotGreaterThanOrEquals(
                offsetInBits + lengthInBits,
                array.length * (long) Long.SIZE,
                "offsetInBits + lengthInBits",
                "array.length * (long)Long.SIZE");

        if (lengthInBits == 0) {
            return 0;
        }

        final int offsetInLongs = (int) (offsetInBits / Long.SIZE);
        final int offsetMod64 = (int) (offsetInBits % Long.SIZE);

        long full = array[offsetInLongs] << offsetMod64;

        if (offsetMod64 + lengthInBits > Long.SIZE) {
            full |= array[offsetInLongs + 1] >>> (Long.SIZE - offsetMod64);
        }

        return full >>> (Long.SIZE - lengthInBits);
    }

    public static void setPartialLong(
            final long[] array, final long offsetInBits, long partialLong, final long lengthInBits) {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offsetInBits, 0, "offsetInBits");
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");
        Argument.assertNotGreaterThan(
                offsetInBits + lengthInBits,
                array.length * (long) Long.SIZE,
                "offsetInBits + lengthInBits",
                "array.length * (long)Long.SIZE");

        if (lengthInBits == 0) {
            return;
        }

        final int offsetInLongs = (int) (offsetInBits / Long.SIZE);
        final int offsetMod64 = (int) (offsetInBits % Long.SIZE);
        final long first = array[offsetInLongs];

        partialLong <<= (int) (Long.SIZE - lengthInBits);

        if (offsetMod64 + lengthInBits <= Long.SIZE) {
            long mask = 1L << (Long.SIZE - 1L);

            mask >>= (int) (lengthInBits - 1L);
            partialLong >>>= offsetMod64;
            mask >>>= offsetMod64;
            array[offsetInLongs] = (first & ~mask) | partialLong;
        } else {
            final long second = array[offsetInLongs + 1];
            final int firstLengthInBits = Long.SIZE - offsetMod64;
            final int secondLengthInBits = (int) lengthInBits - firstLengthInBits;

            array[offsetInLongs] = first >>> firstLengthInBits << firstLengthInBits | partialLong >>> offsetMod64;
            array[offsetInLongs + 1] =
                    partialLong << firstLengthInBits | second << secondLengthInBits >>> secondLengthInBits;
        }
    }

    public static void setLeftJustifiedPartialLong(
            final long[] array, final long offset, final long partialLong, final long lengthInBits) {
        setPartialLong(array, offset, partialLong >>> (Long.SIZE - lengthInBits), lengthInBits);
    }

    public static long getBit(final long[] array, long offset) {
        return getPartialLong(array, offset, 1);
    }

    public static void setBit(long[] array, long offset, long bit) {
        setPartialLong(array, offset, bit & 1L, 1);
    }

    public static void copy(
            final long[] left,
            final long leftOffsetInBits,
            final long[] right,
            final long rightOffsetInBits,
            final long lengthInBits) {
        performBitFunction(COPY_BIT_FUNCTION, left, leftOffsetInBits, right, rightOffsetInBits, lengthInBits);
    }
}
