package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class CharIlaFactoryDivideFuzzer {

    private CharIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final CharIlaFactoryFuzzerSupport.GetInput input = CharIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final char[] left = CharIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final char[] right = createDivisorArray(length, data);

        final CharIlaFactory leftFactory = CharIlaFactoryFromArray.create(left);

        final CharIlaFactory rightFactory = CharIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final CharIlaFactory divideFactory = CharIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        CharIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(CharIlaFactory leftFactory, CharIlaFactory rightFactory, int length)
            throws Exception {

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final CharIlaFactory invalidBufferFactory = CharIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == CharIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final char[] differentLengthArray = CharIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final CharIlaFactory differentLengthFactory = CharIlaFactoryFromArray.create(differentLengthArray);

        final CharIlaFactory invalidLengthIlaFactory =
                CharIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

        try {
            invalidLengthIlaFactory.create();

            throw new AssertionError("create() accepted factories with different lengths"
                    + " [leftLength="
                    + length
                    + ", rightLength="
                    + differentLength
                    + "]");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static char[] createDivisorArray(int length, FuzzedDataProvider data) {

        final char[] array = new char[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = (char) 1;
                    break;

                case 1:
                    array[i] = (char) 2;
                    break;

                case 2:
                    array[i] = (char) 2;
                    break;

                case 3:
                    array[i] = Character.MAX_VALUE;
                    break;

                case 4:
                    array[i] = (char) 3;
                    break;

                case 5:
                    array[i] = (char) 4;
                    break;

                default:
                    int value = data.consumeInt(1, Character.MAX_VALUE);
                    array[i] = (char) value;
                    break;
            }
        }

        return array;
    }

    private static char expectedValue(char left, char right) {

        return (char) (left / right);
    }
}
// AUTO GENERATED FROM TEMPLATE
