package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class IntIlaFactoryDivideFuzzer {

    private IntIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int[] left = IntIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final int[] right = createDivisorArray(length, data);

        final IntIlaFactory leftFactory = IntIlaFactoryFromArray.create(left);

        final IntIlaFactory rightFactory = IntIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final IntIlaFactory divideFactory = IntIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        IntIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> left[index] / right[index], data);
    }

    private static void testArgumentValidation(IntIlaFactory leftFactory, IntIlaFactory rightFactory, int length)
            throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final IntIlaFactory invalidBufferFactory = IntIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == IntIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final int[] differentLengthArray = IntIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final IntIlaFactory differentLengthFactory = IntIlaFactoryFromArray.create(differentLengthArray);

        final IntIlaFactory invalidLengthFactory = IntIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static int[] createDivisorArray(int length, FuzzedDataProvider data) {

        final int[] array = new int[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 1;
                    break;

                case 1:
                    array[i] = -1;
                    break;

                case 2:
                    array[i] = 2;
                    break;

                case 3:
                    array[i] = Integer.MAX_VALUE;
                    break;

                case 4:
                    array[i] = Integer.MIN_VALUE;
                    break;

                default:
                    final int value = data.consumeInt();
                    array[i] = value == 0 ? 1 : value;
                    break;
            }
        }

        return array;
    }
}
// AUTO GENERATED FROM TEMPLATE
