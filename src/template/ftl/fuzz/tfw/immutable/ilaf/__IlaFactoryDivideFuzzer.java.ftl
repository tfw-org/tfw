// intilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ${NAME}IlaFactoryDivideFuzzer {

    private ${NAME}IlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${TYPE}[] left = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${TYPE}[] right = createDivisorArray(length, data);

        final ${NAME}IlaFactory leftFactory = ${NAME}IlaFactoryFromArray.create(left);

        final ${NAME}IlaFactory rightFactory = ${NAME}IlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ${NAME}IlaFactory divideFactory = ${NAME}IlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> left[index] / right[index], data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory leftFactory, ${NAME}IlaFactory rightFactory, int length)
            throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ${NAME}IlaFactory invalidBufferFactory = ${NAME}IlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final ${TYPE}[] differentLengthArray = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final ${NAME}IlaFactory differentLengthFactory = ${NAME}IlaFactoryFromArray.create(differentLengthArray);

        final ${NAME}IlaFactory invalidLengthFactory = ${NAME}IlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static ${TYPE}[] createDivisorArray(int length, FuzzedDataProvider data) {

        final ${TYPE}[] array = new ${TYPE}[length];

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
