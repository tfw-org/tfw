package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class IntIlaFactoryAddFuzzer {

    private IntIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int[] left = IntIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final int[] right = IntIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final IntIlaFactory leftFactory = IntIlaFactoryFromArray.create(left);

        final IntIlaFactory rightFactory = IntIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final IntIlaFactory addFactory = IntIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        IntIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> left[index] + right[index], data);
    }

    private static void testArgumentValidation(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int length)
            throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final IntIlaFactory invalidBufferFactory = IntIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == IntIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final IntIlaFactory differentLengthFactory =
                IntIlaFactoryFromArray.create(IntIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final IntIlaFactory invalidLengthFactory = IntIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

        try {
            invalidLengthFactory.create();

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
}
// AUTO GENERATED FROM TEMPLATE
