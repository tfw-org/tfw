package tfw.immutable.ilaf.booleanilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.IOException;
import tfw.immutable.ila.booleanila.AbstractBooleanIla;
import tfw.immutable.ila.booleanila.BooleanIla;

final class BooleanIlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private BooleanIlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static boolean[] createInitializedArray(int length, FuzzedDataProvider data) {

        final boolean[] array = new boolean[length];
        initialize(array, data);
        return array;
    }

    static boolean[] createInitializedArray(int length) {
        final boolean[] array = new boolean[length];
        initialize(array);
        return array;
    }

    static BooleanIla createIlaWithLengthException() {

        return new AbstractBooleanIla() {

            @Override
            protected long lengthImpl() throws IOException {
                throw new IOException("Test length exception");
            }

            @Override
            protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
                throw new AssertionError("getImpl() should not be called");
            }

            @Override
            protected void closeImpl() throws IOException {
                // Nothing to close.
            }
        };
    }

    static void initialize(boolean[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(boolean[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (i & 1) != 0;
        }
    }

    private static boolean initializeValue(int index, FuzzedDataProvider data) {

        switch (index & 7) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                return data.consumeBoolean();
        }
    }

    static void verifyIla(
            BooleanIla ila, long expectedLen, GetInput input, ExpectedValue expectedVal, FuzzedDataProvider data)
            throws Exception {
        if (ila.length() != expectedLen) {
            throw new AssertionError("Incorrect length: expected=" + expectedLen + ", actual=" + ila.length());
        }

        if (expectedLen > 0) {
            final boolean[] destination = new boolean[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedVal.apply(0), destination[0], 0);
        }

        final boolean[] destination = new boolean[input.destinationLength];

        initialize(destination, data);

        final boolean[] before = destination.clone();

        final boolean valid = isValidGet(expectedLen, input.destinationLength, input.offset, input.start, input.length);

        try {
            ila.get(destination, input.offset, input.start, input.length);

            if (!valid) {
                throw new AssertionError("get() accepted invalid arguments"
                        + " [length="
                        + expectedLen
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

            verifyGet(destination, before, input.offset, input.start, input.length, expectedVal);

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
            ila.get(new boolean[1], 0, 0, 0);

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

        if (offset > destinationLength || start > ilaLength) {
            return false;
        }

        if ((long) offset + length > destinationLength) {
            return false;
        }

        return (long) length <= ilaLength - start;
    }

    private static void verifyGet(
            boolean[] destination, boolean[] before, int offset, long start, int length, ExpectedValue expectedValue) {

        if (length == 0) {
            assertUnchanged(before, destination);
            return;
        }

        final int sourceIndex = Math.toIntExact(start);

        for (int i = 0; i < length; i++) {
            assertEquals(expectedValue.apply(sourceIndex + i), destination[offset + i], sourceIndex + i);
        }

        for (int i = 0; i < destination.length; i++) {
            if (i < offset || i >= offset + length) {
                assertEquals(before[i], destination[i], i);
            }
        }
    }

    static void assertUnchanged(boolean[] before, boolean[] actual) {

        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i], actual[i], i);
        }
    }

    static void assertEquals(boolean expected, boolean actual, int index) {
        if (expected != actual) {
            throw new AssertionError(
                    "Incorrect value at index " + index + ": expected=" + expected + ", actual=" + actual);
        }
    }

    interface ExpectedValue {
        boolean apply(int index);
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
// AUTO GENERATED FROM TEMPLATE
