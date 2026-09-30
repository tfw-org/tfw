package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ShortIlaFactoryAddFuzzer {

    private ShortIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ShortIlaFactoryFuzzerSupport.GetInput input = ShortIlaFactoryFuzzerSupport.consumeGetInput(data);

        final short[] left = ShortIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final short[] right = ShortIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ShortIlaFactory leftFactory = ShortIlaFactoryFromArray.create(left);

        final ShortIlaFactory rightFactory = ShortIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ShortIlaFactory addFactory = ShortIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        ShortIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory, int length)
            throws Exception {

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ShortIlaFactory invalidBufferFactory = ShortIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ShortIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final ShortIlaFactory differentLengthFactory =
                ShortIlaFactoryFromArray.create(ShortIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final ShortIlaFactory invalidLenFactory = ShortIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

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

    private static short expectedValue(short left, short right) {

        return (short) (left + right);
    }
}
// AUTO GENERATED FROM TEMPLATE
