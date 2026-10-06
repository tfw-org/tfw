package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.IOException;
import tfw.immutable.ila.floatila.AbstractFloatIla;
import tfw.immutable.ila.floatila.FloatIla;

final class FloatIlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private FloatIlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static float[] createInitializedArray(int length, FuzzedDataProvider data) {

        final float[] array = new float[length];
        initialize(array, data);
        return array;
    }

    static float[] createInitializedArray(int length) {
        final float[] array = new float[length];
        initialize(array);
        return array;
    }

    static FloatIla createIlaWithLengthException() {

        return new AbstractFloatIla() {

            @Override
            protected long lengthImpl() throws IOException {
                throw new IOException("Test length exception");
            }

            @Override
            protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
                throw new AssertionError("getImpl() should not be called");
            }

            @Override
            protected void closeImpl() throws IOException {
                // Nothing to close.
            }
        };
    }

    static void initialize(float[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(float[] array) {
        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 0.0f;
                    break;
                case 1:
                    array[i] = -0.0f;
                    break;
                case 2:
                    array[i] = Float.NaN;
                    break;
                case 3:
                    array[i] = Float.POSITIVE_INFINITY;
                    break;
                case 4:
                    array[i] = Float.NEGATIVE_INFINITY;
                    break;
                case 5:
                    array[i] = Float.MIN_VALUE;
                    break;
                case 6:
                    array[i] = Float.MAX_VALUE;
                    break;
                default:
                    array[i] = i * 1.2345678f;
                    break;
            }
        }
    }

    private static float initializeValue(int index, FuzzedDataProvider data) {

        switch (index & 7) {
            case 0:
                return 0.0f;
            case 1:
                return -0.0f;
            case 2:
                return Float.NaN;
            case 3:
                return Float.POSITIVE_INFINITY;
            case 4:
                return Float.NEGATIVE_INFINITY;
            case 5:
                return Float.MIN_VALUE;
            case 6:
                return Float.MAX_VALUE;
            default:
                return data.consumeFloat();
        }
    }

    static void verifyIla(
            FloatIla ila, long expectedLen, GetInput input, ExpectedValue expectedVal, FuzzedDataProvider data)
            throws Exception {
        if (ila.length() != expectedLen) {
            throw new AssertionError("Incorrect length: expected=" + expectedLen + ", actual=" + ila.length());
        }

        if (expectedLen > 0) {
            final float[] destination = new float[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedVal.apply(0), destination[0], 0);
        }

        final float[] destination = new float[input.destinationLength];

        initialize(destination, data);

        final float[] before = destination.clone();

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
            ila.get(new float[1], 0, 0, 0);

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
            float[] destination, float[] before, int offset, long start, int length, ExpectedValue expectedValue) {

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

    static void assertUnchanged(float[] before, float[] actual) {

        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i], actual[i], i);
        }
    }

    static void assertEquals(float expected, float actual, int index) {
        int expectedBits = Float.floatToRawIntBits(expected);
        int actualBits = Float.floatToRawIntBits(actual);
        if (expectedBits != actualBits) {
            throw new AssertionError("Incorrect value at index "
                    + index
                    + ": expectedBits="
                    + Integer.toHexString(expectedBits)
                    + ", actualBits="
                    + Integer.toHexString(actualBits));
        }
    }

    interface ExpectedValue {
        float apply(int index);
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
