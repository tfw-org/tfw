package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.IOException;
import tfw.immutable.ila.shortila.AbstractShortIla;
import tfw.immutable.ila.shortila.ShortIla;

final class ShortIlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private ShortIlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static short[] createInitializedArray(int length, FuzzedDataProvider data) {

        final short[] array = new short[length];
        initialize(array, data);
        return array;
    }

    static short[] createInitializedArray(int length) {
        final short[] array = new short[length];
        initialize(array);
        return array;
    }

    static ShortIla createIlaWithLengthException() {

        return new AbstractShortIla() {

            @Override
            protected long lengthImpl() throws IOException {
                throw new IOException("Test length exception");
            }

            @Override
            protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
                throw new AssertionError("getImpl() should not be called");
            }

            @Override
            protected void closeImpl() throws IOException {
                // Nothing to close.
            }
        };
    }

    static void initialize(short[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(short[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (short) (i * 7919 + 12345);
        }
    }

    private static short initializeValue(int index, FuzzedDataProvider data) {

        switch (index & 7) {
            case 0:
                return Short.MIN_VALUE;
            case 1:
                return Short.MAX_VALUE;
            case 2:
                return 0;
            case 3:
                return -1;
            case 4:
                return 1;
            default:
                return (short) data.consumeInt();
        }
    }

    static void verifyIla(
            ShortIla ila, long expectedLen, GetInput input, ExpectedValue expectedVal, FuzzedDataProvider data)
            throws Exception {
        if (ila.length() != expectedLen) {
            throw new AssertionError("Incorrect length: expected=" + expectedLen + ", actual=" + ila.length());
        }

        if (expectedLen > 0) {
            final short[] destination = new short[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedVal.apply(0), destination[0], 0);
        }

        final short[] destination = new short[input.destinationLength];

        initialize(destination, data);

        final short[] before = destination.clone();

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
            ila.get(new short[1], 0, 0, 0);

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
            short[] destination, short[] before, int offset, long start, int length, ExpectedValue expectedValue) {

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

    static void assertUnchanged(short[] before, short[] actual) {

        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i], actual[i], i);
        }
    }

    static void assertEquals(short expected, short actual, int index) {
        if (expected != actual) {
            throw new AssertionError(
                    "Incorrect value at index " + index + ": expected=" + expected + ", actual=" + actual);
        }
    }

    interface ExpectedValue {
        short apply(int index);
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
