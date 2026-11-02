package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class FloatIlaFactoryAddFuzzer {

    private FloatIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final FloatIlaFactoryFuzzerSupport.GetInput input = FloatIlaFactoryFuzzerSupport.consumeGetInput(data);

        final float[] left = FloatIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final float[] right = FloatIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final FloatIlaFactory leftFactory = FloatIlaFactoryFromArray.create(left);

        final FloatIlaFactory rightFactory = FloatIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final FloatIlaFactory addFactory = FloatIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        FloatIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory, int length)
            throws Exception {

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final FloatIlaFactory throwingLengthFloatIlaFactory =
                () -> FloatIlaFactoryFuzzerSupport.createIlaWithLengthException();

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryAdd.create(throwingLengthFloatIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryAdd.create(leftFactory, throwingLengthFloatIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final FloatIlaFactory invalidBufferFactory = FloatIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == FloatIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final FloatIlaFactory differentLengthFactory =
                FloatIlaFactoryFromArray.create(FloatIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final FloatIlaFactory invalidLenFactory = FloatIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

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

    private static float expectedValue(float left, float right) {

        return left + right;
    }
}
// AUTO GENERATED FROM TEMPLATE
