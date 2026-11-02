package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class FloatIlaFactoryDivideFuzzer {

    private FloatIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final FloatIlaFactoryFuzzerSupport.GetInput input = FloatIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final float[] left = FloatIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final float[] right = createDivisorArray(length, data);

        final FloatIlaFactory leftFactory = FloatIlaFactoryFromArray.create(left);

        final FloatIlaFactory rightFactory = FloatIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final FloatIlaFactory divideFactory = FloatIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        FloatIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(FloatIlaFactory leftFactory, FloatIlaFactory rightFactory, int length)
            throws Exception {

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final FloatIlaFactory throwingLengthFloatIlaFactory =
                () -> FloatIlaFactoryFuzzerSupport.createIlaWithLengthException();

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryDivide.create(throwingLengthFloatIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryDivide.create(leftFactory, throwingLengthFloatIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final FloatIlaFactory invalidBufferFactory = FloatIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == FloatIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final float[] differentLengthArray = FloatIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final FloatIlaFactory differentLengthFactory = FloatIlaFactoryFromArray.create(differentLengthArray);

        final FloatIlaFactory invalidLengthIlaFactory =
                FloatIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static float[] createDivisorArray(int length, FuzzedDataProvider data) {

        final float[] array = new float[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 0.0f;
                    break;

                case 1:
                    array[i] = -0.0f;
                    break;

                case 2:
                    array[i] = 1.0f;
                    break;

                case 3:
                    array[i] = -1.0f;
                    break;

                case 4:
                    array[i] = Float.MIN_VALUE;
                    break;

                case 5:
                    array[i] = Float.MAX_VALUE;
                    break;

                default:
                    array[i] = data.consumeFloat();
                    break;
            }
        }

        return array;
    }

    private static float expectedValue(float left, float right) {

        return left / right;
    }
}
// AUTO GENERATED FROM TEMPLATE
