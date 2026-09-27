package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryAddFuzzer {
    private static final int MAX_LENGTH = 512;

    private IntIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        final int length = data.consumeInt(0, MAX_LENGTH);
        final int destinationLength = data.consumeInt(0, MAX_LENGTH);
        final int offset = data.consumeInt();
        final long start = data.consumeLong();
        final int getLength = data.consumeInt();

        final int[] left = new int[length];
        final int[] right = new int[length];

        initialize(left, data);
        initialize(right, data);

        final IntIlaFactory leftFactory = IntIlaFactoryFromArray.create(left);
        final IntIlaFactory rightFactory = IntIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        /*
         * The two factories must have matching lengths.
         */
        final IntIlaFactory addFactory = IntIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        final IntIla ila = addFactory.create();

        try {
            if (ila.length() != length) {
                throw new AssertionError("Incorrect length: expected=" + length + ", actual=" + ila.length());
            }

            /*
             * Verify the first element separately when one exists.
             */
            if (length > 0) {
                final int[] destination = new int[1];

                ila.get(destination, 0, 0, 1);

                assertEquals(left[0] + right[0], destination[0], 0);
            }

            /*
             * Exercise the fuzzed get() operation.
             */
            final int[] destination = new int[destinationLength];

            initialize(destination, data);

            final int[] before = destination.clone();

            final boolean valid = isValidGet(length, destinationLength, offset, start, getLength);

            try {
                ila.get(destination, offset, start, getLength);

                if (!valid) {
                    throw new AssertionError("get() accepted invalid arguments"
                            + " [length=" + length
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

    private static void testArgumentValidation(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int length)
            throws Exception {
        /*
         * The factory arguments themselves must not be null.
         */
        try {
            IntIlaFactoryAdd.create(null, rightFactory, 1);

            throw new AssertionError("create() accepted null leftFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        try {
            IntIlaFactoryAdd.create(leftFactory, null, 1);

            throw new AssertionError("create() accepted null rightFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * bufferSize must be at least one. This validation occurs when
         * the resulting factory creates the underlying ILA.
         */
        final IntIlaFactory invalidBufferSizeFactory = IntIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            final IntIla ila = invalidBufferSizeFactory.create();

            try {
                throw new AssertionError("create() accepted bufferSize=0");

            } finally {
                ila.close();
            }

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * The left and right ILAs must have matching lengths. This
         * validation also occurs when the resulting factory creates
         * the underlying ILA.
         */
        final int[] mismatchedRight = new int[length + 1];

        final IntIlaFactory mismatchedRightFactory = IntIlaFactoryFromArray.create(mismatchedRight);

        final IntIlaFactory mismatchedLengthsFactory = IntIlaFactoryAdd.create(leftFactory, mismatchedRightFactory, 1);

        try {
            final IntIla ila = mismatchedLengthsFactory.create();

            try {
                throw new AssertionError("create() accepted mismatched factory lengths"
                        + " [leftLength=" + length
                        + ", rightLength=" + (length + 1)
                        + "]");

            } finally {
                ila.close();
            }

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static void initialize(int[] array, FuzzedDataProvider data) {
        for (int i = 0; i < array.length; i++) {
            /*
             * Mixing fuzzer-controlled values with deterministic
             * edge-oriented values gives us useful arithmetic cases
             * without requiring a large input.
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

        final int sourceIndex = Math.toIntExact(start);

        for (int i = 0; i < length; i++) {
            final int expected = left[sourceIndex + i] + right[sourceIndex + i];

            assertEquals(expected, destination[offset + i], sourceIndex + i);
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
