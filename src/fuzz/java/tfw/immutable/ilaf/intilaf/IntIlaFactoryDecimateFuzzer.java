package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryDecimateFuzzer {
    private static final int MAX_LENGTH = 512;

    private IntIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        final int sourceLength = data.consumeInt(0, MAX_LENGTH);
        final int destinationLength = data.consumeInt(0, MAX_LENGTH);
        final int offset = data.consumeInt();
        final long start = data.consumeLong();
        final int getLength = data.consumeInt();
        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, MAX_LENGTH);

        final int[] source = new int[sourceLength];
        final int[] buffer = new int[bufferLength];

        initialize(source, data);
        initialize(buffer, data);

        final IntIlaFactory sourceFactory = IntIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        /*
         * The factory itself only validates the source factory. The factor
         * and buffer are validated when the resulting factory creates the
         * ILA.
         */
        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final IntIlaFactory decimateFactory = IntIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final IntIla ila;

        try {
            ila = decimateFactory.create();
        } catch (IllegalArgumentException e) {
            /*
             * A valid factor and buffer should be accepted. If this happens,
             * the implementation has rejected an otherwise valid factory.
             */
            throw new AssertionError(
                    "create() rejected valid arguments"
                            + " [sourceLength="
                            + sourceLength
                            + ", factor="
                            + factor
                            + ", bufferLength="
                            + bufferLength
                            + "]",
                    e);
        }

        try {
            final long expectedLength = expectedLength(sourceLength, factor);

            if (ila.length() != expectedLength) {
                throw new AssertionError("Incorrect length: expected="
                        + expectedLength
                        + ", actual="
                        + ila.length()
                        + " [sourceLength="
                        + sourceLength
                        + ", factor="
                        + factor
                        + "]");
            }

            /*
             * Verify the first decimated value separately when one exists.
             */
            if (expectedLength > 0) {
                final int[] destination = new int[1];

                ila.get(destination, 0, 0, 1);

                assertEquals(source[0], destination[0], 0);
            }

            /*
             * Exercise the fuzzed get() operation.
             */
            final int[] destination = new int[destinationLength];

            initialize(destination, data);

            final int[] before = destination.clone();

            final boolean valid = isValidGet(expectedLength, destinationLength, offset, start, getLength);

            try {
                ila.get(destination, offset, start, getLength);

                if (!valid) {
                    throw new AssertionError("get() accepted invalid arguments"
                            + " [length="
                            + expectedLength
                            + ", destinationLength="
                            + destinationLength
                            + ", offset="
                            + offset
                            + ", start="
                            + start
                            + ", getLength="
                            + getLength
                            + "]");
                }

                verifyGet(source, factor, destination, before, offset, start, getLength);

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

    private static void testArgumentValidation(IntIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        /*
         * The source factory itself must not be null.
         */
        try {
            IntIlaFactoryDecimate.create(null, 2, new int[10]);

            throw new AssertionError("create() accepted null ilaFactory");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        /*
         * Invalid factor and buffer length are validated when the
         * resulting factory creates the underlying ILA.
         */
        if (factor < 2) {
            final IntIlaFactory invalidFactorFactory = IntIlaFactoryDecimate.create(sourceFactory, factor, new int[1]);

            try {
                final IntIla ila = invalidFactorFactory.create();

                try {
                    throw new AssertionError("create() accepted invalid factor [factor=" + factor + "]");

                } finally {
                    ila.close();
                }

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final IntIlaFactory invalidBufferFactory = IntIlaFactoryDecimate.create(sourceFactory, 2, new int[0]);

            try {
                final IntIla ila = invalidBufferFactory.create();

                try {
                    throw new AssertionError("create() accepted bufferLength=0");

                } finally {
                    ila.close();
                }

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }
    }

    private static long expectedLength(int sourceLength, long factor) {
        /*
         * Avoid the implementation's potentially overflowing expression:
         *
         *     (sourceLength + factor - 1) / factor
         *
         * Since sourceLength is non-negative, this equivalent formulation
         * safely handles very large factors.
         */
        if (sourceLength == 0) {
            return 0;
        }

        return 1 + (sourceLength - 1L) / factor;
    }

    private static void initialize(int[] array, FuzzedDataProvider data) {
        for (int i = 0; i < array.length; i++) {
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

    private static boolean isValidGet(long ilaLength, int destinationLength, int offset, long start, int length) {

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
            int[] source, long factor, int[] destination, int[] before, int offset, long start, int length) {

        if (length == 0) {
            assertUnchanged(before, destination);
            return;
        }

        for (int i = 0; i < length; i++) {
            final long sourceIndex = (start + i) * factor;
            final int expected = source[Math.toIntExact(sourceIndex)];

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
