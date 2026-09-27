package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.util.function.IntUnaryOperator;
import tfw.immutable.ila.intila.IntIla;

final class IntIlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private IntIlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static int[] createInitializedArray(int length, FuzzedDataProvider data) {

        final int[] array = new int[length];
        initialize(array, data);
        return array;
    }

    static int[] createInitializedArray(int length) {
        final int[] array = new int[length];
        initialize(array);
        return array;
    }

    static void initialize(int[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i);
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

    private static int initializeValue(int index) {
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
                return index * 0x9e3779b9 ^ 0x12345678;
        }
    }

    static void verifyIla(
            IntIla ila, long expectedLength, GetInput input, IntUnaryOperator expectedValue, FuzzedDataProvider data)
            throws Exception {

        if (ila.length() != expectedLength) {
            throw new AssertionError("Incorrect length: expected=" + expectedLength + ", actual=" + ila.length());
        }

        if (expectedLength > 0) {
            final int[] destination = new int[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedValue.applyAsInt(0), destination[0], 0);
        }

        final int[] destination = new int[input.destinationLength];

        initialize(destination, data);

        final int[] before = destination.clone();

        final boolean valid =
                isValidGet(expectedLength, input.destinationLength, input.offset, input.start, input.length);

        try {
            ila.get(destination, input.offset, input.start, input.length);

            if (!valid) {
                throw new AssertionError("get() accepted invalid arguments"
                        + " [length="
                        + expectedLength
                        + ", destinationLength="
                        + input.destinationLength
                        + ", offset="
                        + input.offset
                        + ", start="
                        + input.start
                        + ", getLength="
                        + input.length
                        + "]");
            }

            verifyGet(destination, before, input.offset, input.start, input.length, expectedValue);

        } catch (IllegalArgumentException e) {
            if (valid) {
                throw new AssertionError("get() rejected valid arguments", e);
            }

            assertUnchanged(before, destination);
        }

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
    }

    static void expectIllegalArgumentException(ThrowingRunnable action, String message) throws Exception {

        try {
            action.run();

            throw new AssertionError(message);

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    static boolean isValidGet(long ilaLength, int destinationLength, int offset, long start, int length) {

        if (offset < 0 || start < 0 || length < 0) {
            return false;
        }

        if (offset >= destinationLength || start >= ilaLength) {
            return false;
        }

        if ((long) offset + length > destinationLength) {
            return false;
        }

        return start + (long) length <= ilaLength;
    }

    private static void verifyGet(
            int[] destination, int[] before, int offset, long start, int length, IntUnaryOperator expectedValue) {

        if (length == 0) {
            assertUnchanged(before, destination);
            return;
        }

        final int sourceIndex = Math.toIntExact(start);

        for (int i = 0; i < length; i++) {
            assertEquals(expectedValue.applyAsInt(sourceIndex + i), destination[offset + i], sourceIndex + i);
        }

        for (int i = 0; i < destination.length; i++) {
            if (i < offset || i >= offset + length) {
                assertEquals(before[i], destination[i], i);
            }
        }
    }

    static void assertUnchanged(int[] before, int[] actual) {

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

    static void assertEquals(int expected, int actual, int index) {

        if (expected != actual) {
            throw new AssertionError(
                    "Incorrect value at index " + index + ": expected=" + expected + ", actual=" + actual);
        }
    }

    interface ThrowingRunnable {
        void run() throws Exception;
    }

    static final class GetInput {
        final int destinationLength;
        final int offset;
        final long start;
        final int length;

        GetInput(int destinationLength, int offset, long start, int length) {
            this.destinationLength = destinationLength;
            this.offset = offset;
            this.start = start;
            this.length = length;
        }
    }
}
