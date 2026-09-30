package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class CharIlaFactoryAddFuzzer {

    private CharIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final CharIlaFactoryFuzzerSupport.GetInput input = CharIlaFactoryFuzzerSupport.consumeGetInput(data);

        final char[] left = CharIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final char[] right = CharIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final CharIlaFactory leftFactory = CharIlaFactoryFromArray.create(left);

        final CharIlaFactory rightFactory = CharIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final CharIlaFactory addFactory = CharIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        CharIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(CharIlaFactory leftFactory, CharIlaFactory rightFactory, int length)
            throws Exception {

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final CharIlaFactory invalidBufferFactory = CharIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == CharIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final CharIlaFactory differentLengthFactory =
                CharIlaFactoryFromArray.create(CharIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final CharIlaFactory invalidLenFactory = CharIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

        try {
            invalidLenFactory.create();

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

    private static char expectedValue(char left, char right) {

        return (char) (left + right);
    }
}
// AUTO GENERATED FROM TEMPLATE
