package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryBoundFuzzer {
    private static final int MAX_LENGTH = 512;

    private IntIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        final int length = data.consumeInt(0, MAX_LENGTH);
        final int destinationLength = data.consumeInt(0, MAX_LENGTH);
        final int offset = data.consumeInt();
        final long start = data.consumeLong();
        final int getLength = data.consumeInt();

        final int[] source = new int[length];

        initialize(source, data);

        final IntIlaFactory sourceFactory = IntIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, length);

        /*
         * Generate an ordered minimum/maximum pair so that the main
         * bounded ILA is valid.
         */
        final int minimum = data.consumeInt();
        final int maximum = data.consumeInt();

        final int actualMinimum = Math.min(minimum, maximum);
        final int actualMaximum = Math.max(minimum, maximum);

        final IntIlaFactory boundedFactory = IntIlaFactoryBound.create(sourceFactory, actualMinimum, actualMaximum);

        final IntIla ila = boundedFactory.create();

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

                assertEquals(bound(source[0], actualMinimum, actualMaximum), destination[0], 0);
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

                verifyGet(source, destination, before, offset, start, getLength, actualMinimum, actualMaximum);

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

    private static void testArgumentValidation(IntIlaFactory sourceFactory, int length) throws Exception {

        /*
         * The source factory itself must not be null.
         */
        try {
            IntIlaFactoryBound.create(null, 0, 1);

            throw new AssertionError("create() accepted null ilaFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * minimum must not be greater than maximum. This validation
         * occurs when the resulting factory creates the underlying ILA.
         */
        final IntIlaFactory invalidBoundsFactory = IntIlaFactoryBound.create(sourceFactory, 1, 0);

        try {
            final IntIla ila = invalidBoundsFactory.create();

            try {
                throw new AssertionError("create() accepted minimum > maximum");

            } finally {
                ila.close();
            }

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * Exercise the empty-ILA case with otherwise valid bounds.
         */
        if (length == 0) {
            final IntIlaFactory emptyFactory = IntIlaFactoryBound.create(sourceFactory, 0, 0);

            final IntIla ila = emptyFactory.create();

            try {
                if (ila.length() != 0) {
                    throw new AssertionError("Incorrect empty ILA length: " + ila.length());
                }
            } finally {
                ila.close();
            }
        }
    }

    private static int initializeValue(int index, FuzzedDataProvider data) {
        switch (index & 7) {
            case 0:
                return Integer.MIN_VALUE;

            case 1:
                return Integer.MAX_VALUE;

            case 2:
                return 0;

            case 3:
                return -1;

            case 4:
                return 1;

            default:
                return data.consumeInt();
        }
    }

    private static void initialize(int[] array, FuzzedDataProvider data) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    private static int bound(int value, int minimum, int maximum) {
        if (value < minimum) {
            return minimum;
        }

        if (value > maximum) {
            return maximum;
        }

        return value;
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
            int[] source,
            int[] destination,
            int[] before,
            int offset,
            long start,
            int length,
            int minimum,
            int maximum) {

        if (length == 0) {
            assertUnchanged(before, destination);
            return;
        }

        final int sourceIndex = Math.toIntExact(start);

        for (int i = 0; i < length; i++) {
            final int expected = bound(source[sourceIndex + i], minimum, maximum);

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
