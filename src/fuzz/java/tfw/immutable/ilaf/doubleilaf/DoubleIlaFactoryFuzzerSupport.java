package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.IOException;
import tfw.immutable.ila.doubleila.AbstractDoubleIla;
import tfw.immutable.ila.doubleila.DoubleIla;

final class DoubleIlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private DoubleIlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static double[] createInitializedArray(int length, FuzzedDataProvider data) {

        final double[] array = new double[length];
        initialize(array, data);
        return array;
    }

    static double[] createInitializedArray(int length) {
        final double[] array = new double[length];
        initialize(array);
        return array;
    }

    static DoubleIla createIlaWithLengthException() {

        return new AbstractDoubleIla() {

            @Override
            protected long lengthImpl() throws IOException {
                throw new IOException("Test length exception");
            }

            @Override
            protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
                throw new AssertionError("getImpl() should not be called");
            }

            @Override
            protected void closeImpl() throws IOException {
                // Nothing to close.
            }
        };
    }

    static void initialize(double[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(double[] array) {
        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 0.0;
                    break;
                case 1:
                    array[i] = -0.0;
                    break;
                case 2:
                    array[i] = Double.NaN;
                    break;
                case 3:
                    array[i] = Double.POSITIVE_INFINITY;
                    break;
                case 4:
                    array[i] = Double.NEGATIVE_INFINITY;
                    break;
                case 5:
                    array[i] = Double.MIN_VALUE;
                    break;
                case 6:
                    array[i] = Double.MAX_VALUE;
                    break;
                default:
                    array[i] = i * 1.23456789;
                    break;
            }
        }
    }

    private static double initializeValue(int index, FuzzedDataProvider data) {

        switch (index & 7) {
            case 0:
                return 0.0;
            case 1:
                return -0.0;
            case 2:
                return Double.NaN;
            case 3:
                return Double.POSITIVE_INFINITY;
            case 4:
                return Double.NEGATIVE_INFINITY;
            case 5:
                return Double.MIN_VALUE;
            case 6:
                return Double.MAX_VALUE;
            default:
                return data.consumeDouble();
        }
    }

    static void verifyIla(
            DoubleIla ila, long expectedLen, GetInput input, ExpectedValue expectedVal, FuzzedDataProvider data)
            throws Exception {
        if (ila.length() != expectedLen) {
            throw new AssertionError("Incorrect length: expected=" + expectedLen + ", actual=" + ila.length());
        }

        if (expectedLen > 0) {
            final double[] destination = new double[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedVal.apply(0), destination[0], 0);
        }

        final double[] destination = new double[input.destinationLength];

        initialize(destination, data);

        final double[] before = destination.clone();

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
            ila.get(new double[1], 0, 0, 0);

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
            double[] destination, double[] before, int offset, long start, int length, ExpectedValue expectedValue) {

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

    static void assertUnchanged(double[] before, double[] actual) {

        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i], actual[i], i);
        }
    }

    static void assertEquals(double expected, double actual, int index) {
        long expectedBits = Double.doubleToRawLongBits(expected);
        long actualBits = Double.doubleToRawLongBits(actual);
        if (expectedBits != actualBits) {
            throw new AssertionError("Incorrect value at index "
                    + index
                    + ": expectedBits="
                    + Long.toHexString(expectedBits)
                    + ", actualBits="
                    + Long.toHexString(actualBits));
        }
    }

    interface ExpectedValue {
        double apply(int index);
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
