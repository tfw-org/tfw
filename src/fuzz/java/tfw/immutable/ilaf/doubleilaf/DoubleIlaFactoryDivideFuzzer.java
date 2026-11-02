package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class DoubleIlaFactoryDivideFuzzer {

    private DoubleIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final DoubleIlaFactoryFuzzerSupport.GetInput input = DoubleIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final double[] left = DoubleIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final double[] right = createDivisorArray(length, data);

        final DoubleIlaFactory leftFactory = DoubleIlaFactoryFromArray.create(left);

        final DoubleIlaFactory rightFactory = DoubleIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final DoubleIlaFactory divideFactory = DoubleIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        DoubleIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory, int length)
            throws Exception {

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final DoubleIlaFactory throwingLengthDoubleIlaFactory =
                () -> DoubleIlaFactoryFuzzerSupport.createIlaWithLengthException();

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryDivide.create(throwingLengthDoubleIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryDivide.create(leftFactory, throwingLengthDoubleIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final DoubleIlaFactory invalidBufferFactory = DoubleIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == DoubleIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final double[] differentLengthArray = DoubleIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final DoubleIlaFactory differentLengthFactory = DoubleIlaFactoryFromArray.create(differentLengthArray);

        final DoubleIlaFactory invalidLengthIlaFactory =
                DoubleIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static double[] createDivisorArray(int length, FuzzedDataProvider data) {

        final double[] array = new double[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 0.0d;
                    break;

                case 1:
                    array[i] = -0.0d;
                    break;

                case 2:
                    array[i] = 1.0d;
                    break;

                case 3:
                    array[i] = -1.0d;
                    break;

                case 4:
                    array[i] = Double.MIN_VALUE;
                    break;

                case 5:
                    array[i] = Double.MAX_VALUE;
                    break;

                default:
                    array[i] = data.consumeDouble();
                    break;
            }
        }

        return array;
    }

    private static double expectedValue(double left, double right) {

        return left / right;
    }
}
// AUTO GENERATED FROM TEMPLATE
