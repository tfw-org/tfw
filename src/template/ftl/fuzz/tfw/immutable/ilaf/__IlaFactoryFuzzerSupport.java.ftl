// booleanilaf,byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,objectilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.${LOWERCASE}ila.${NAME}Ila;

final class ${NAME}IlaFactoryFuzzerSupport {

    static final int MAX_LENGTH = 512;

    private ${NAME}IlaFactoryFuzzerSupport() {}

    static GetInput consumeGetInput(FuzzedDataProvider data) {
        return new GetInput(data.consumeInt(0, MAX_LENGTH), data.consumeInt(), data.consumeLong(), data.consumeInt());
    }

    static ${TYPE}[] createInitializedArray(int length, FuzzedDataProvider data) {

        final ${TYPE}[] array = new ${TYPE}[length];
        initialize(array, data);
        return array;
    }

    static ${TYPE}[] createInitializedArray(int length) {
        final ${TYPE}[] array = new ${TYPE}[length];
        initialize(array);
        return array;
    }

    static void initialize(${TYPE}[] array, FuzzedDataProvider data) {

        for (int i = 0; i < array.length; i++) {
            array[i] = initializeValue(i, data);
        }
    }

    static void initialize(${TYPE}[] array) {
${FUZZ_SUPPORT_INITIALIZE}
    }

    private static ${TYPE} initializeValue(int index, FuzzedDataProvider data) {

${FUZZ_RANDOM_INITIALIZE}
    }

    static void verifyIla(
            ${NAME}Ila<#if TYPE == "Object"><Object></#if> ila, long expectedLen, GetInput input, ExpectedValue expectedVal, FuzzedDataProvider data)
            throws Exception {
        if (ila.length() != expectedLen) {
            throw new AssertionError("Incorrect length: expected=" + expectedLen + ", actual=" + ila.length());
        }

        if (expectedLen > 0) {
            final ${TYPE}[] destination = new ${TYPE}[1];

            ila.get(destination, 0, 0, 1);

            assertEquals(expectedVal.apply(0), destination[0], 0);
        }

        final ${TYPE}[] destination = new ${TYPE}[input.destinationLength];

        initialize(destination, data);

        final ${TYPE}[] before = destination.clone();

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
            ila.get(new ${TYPE}[1], 0, 0, 0);

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
            ${TYPE}[] destination, ${TYPE}[] before, int offset, long start, int length, ExpectedValue expectedValue) {

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

    static void assertUnchanged(${TYPE}[] before, ${TYPE}[] actual) {

        if (before.length != actual.length) {
            throw new AssertionError("Array lengths differ: " + before.length + " != " + actual.length);
        }

        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i], actual[i], i);
        }
    }

    static void assertEquals(${TYPE} expected, ${TYPE} actual, int index) {
${FUZZ_ASSERT_VALUE_EQUALS}
    }

    interface ExpectedValue {
        ${TYPE} apply(int index);
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
