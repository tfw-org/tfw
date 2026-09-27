package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryConcatenateFuzzer {
    private static final int MAX_LENGTH = 512;

    private IntIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        final int leftLength = data.consumeInt(0, MAX_LENGTH);
        final int rightLength = data.consumeInt(0, MAX_LENGTH);
        final int destinationLength = data.consumeInt(0, MAX_LENGTH);
        final int offset = data.consumeInt();
        final long start = data.consumeLong();
        final int getLength = data.consumeInt();

        final int[] left = new int[leftLength];
        final int[] right = new int[rightLength];

        initialize(left, data);
        initialize(right, data);

        final IntIlaFactory leftFactory = IntIlaFactoryFromArray.create(left);
        final IntIlaFactory rightFactory = IntIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final IntIlaFactory concatenateFactory = IntIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final IntIla ila = concatenateFactory.create();

        try {
            final int expectedLength = leftLength + rightLength;

            if (ila.length() != expectedLength) {
                throw new AssertionError("Incorrect length: expected=" + expectedLength + ", actual=" + ila.length());
            }

            /*
             * Verify the first element separately when one exists.
             */
            if (expectedLength > 0) {
                final int[] destination = new int[1];

                ila.get(destination, 0, 0, 1);

                final int expected = leftLength > 0 ? left[0] : right[0];

                assertEquals(expected, destination[0], 0);
            }

            /*
             * Verify the element at the concatenation boundary when
             * both sides exist.
             */
            if (leftLength > 0 && rightLength > 0) {
                final int[] destination = new int[1];

                ila.get(destination, 0, leftLength - 1L, 1);
                assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

                ila.get(destination, 0, leftLength, 1);
                assertEquals(right[0], destination[0], leftLength);
            }

            /*
             * Exercise a fuzzed get() operation. In particular, this
             * can cover requests entirely in the left ILA, entirely in
             * the right ILA, and requests that cross the boundary.
             */
            final int[] destination = new int[destinationLength];

            initialize(destination, data);

            final int[] before = destination.clone();

            final boolean valid = isValidGet(expectedLength, destinationLength, offset, start, getLength);

            try {
                ila.get(destination, offset, start, getLength);

                if (!valid) {
                    throw new AssertionError("get() accepted invalid arguments"
                            + " [leftLength=" + leftLength
                            + ", rightLength=" + rightLength
                            + ", destinationLength=" + destinationLength
                            + ", offset=" + offset
                            + ", start=" + start
                            + ", getLength=" + getLength
                            + "]");
                }

                verifyGet(left, right, destination, before, offset, start, getLength);

            } catch (IllegalArgumentException e) {
                if (valid) {
                    throw new AssertionError("get() rejected valid arguments", e);
                }

                assertUnchanged(before, destination);
            }

            /*
             * Verify that closing works and that operations are rejected
             * afterward.
             */
            ila.close();
            ila.close();

            try {
                ila.length();
                throw new AssertionError("length() accepted after close()");

            } catch (IllegalStateException expected) {
                // Correct.
            }

            try {
                ila.get(new int[1], 0, 0, 0);
                throw new AssertionError("get() accepted after close()");

            } catch (IllegalStateException expected) {
                // Correct.
            }

        } finally {
            ila.close();
        }
    }

    private static void testArgumentValidation(IntIlaFactory leftFactory, IntIlaFactory rightFactory) throws Exception {

        /*
         * Both factory arguments must not be null.
         */
        try {
            IntIlaFactoryConcatenate.create(null, rightFactory);

            throw new AssertionError("create() accepted null leftFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        try {
            IntIlaFactoryConcatenate.create(leftFactory, null);

            throw new AssertionError("create() accepted null rightFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * Empty left and right factories are valid and should produce
         * an empty concatenated ILA.
         */
        final IntIlaFactory emptyLeft = IntIlaFactoryFromArray.create(new int[0]);

        final IntIlaFactory emptyRight = IntIlaFactoryFromArray.create(new int[0]);

        final IntIlaFactory emptyConcatenate = IntIlaFactoryConcatenate.create(emptyLeft, emptyRight);

        final IntIla ila = emptyConcatenate.create();

        try {
            if (ila.length() != 0) {
                throw new AssertionError("Incorrect empty concatenated length: " + ila.length());
            }
        } finally {
            ila.close();
        }
    }

    private static void initialize(int[] array, FuzzedDataProvider data) {
        for (int i = 0; i < array.length; i++) {
            /*
             * Include values that make it easy to distinguish the left
             * and right portions and exercise integer edge values.
             */
            switch (i & 7) {
                case 0:
                    array[i] = Integer.MIN_VALUE;
                    break;

                case 1:
                    array[i] = Integer.MAX_VALUE;
                    break;

                case 2:
                    array[i] = 0;
                    break;

                case 3:
                    array[i] = -1;
                    break;

                case 4:
                    array[i] = 1;
                    break;

                default:
                    array[i] = data.consumeInt();
                    break;
            }
        }
    }

    private static boolean isValidGet(int ilaLength, int destinationLength, int offset, long start, int length) {

        if (offset < 0 || start < 0 || length < 0) {
            return false;
        }

        if (offset >= destinationLength || start >= ilaLength) {
            return false;
        }

        if ((long) offset + length > destinationLength) {
            return false;
        }

        if (start + (long) length > ilaLength) {
            return false;
        }

        return true;
    }

    private static void verifyGet(
            int[] left, int[] right, int[] destination, int[] before, int offset, long start, int length) {

        if (length == 0) {
            assertUnchanged(before, destination);
            return;
        }

        final int leftLength = left.length;

        for (int i = 0; i < length; i++) {
            final long sourceIndex = start + i;

            final int expected;

            if (sourceIndex < leftLength) {
                expected = left[Math.toIntExact(sourceIndex)];
            } else {
                expected = right[Math.toIntExact(sourceIndex - leftLength)];
            }

            assertEquals(expected, destination[offset + i], Math.toIntExact(sourceIndex));
        }

        /*
         * Nothing outside the requested destination range may change.
         */
        for (int i = 0; i < destination.length; i++) {
            if (i < offset || i >= offset + length) {
                assertEquals(before[i], destination[i], i);
            }
        }
    }

    private static void assertUnchanged(int[] before, int[] actual) {
        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            if (before[i] != actual[i]) {
                throw new AssertionError(
                        "Destination changed at index " + i + ": expected=" + before[i] + ", actual=" + actual[i]);
            }
        }
    }

    private static void assertEquals(int expected, int actual, int index) {
        if (expected != actual) {
            throw new AssertionError(
                    "Incorrect value at index " + index + ": expected=" + expected + ", actual=" + actual);
        }
    }
}
