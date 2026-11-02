package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class DoubleIlaFactoryAddFuzzer {

    private DoubleIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final DoubleIlaFactoryFuzzerSupport.GetInput input = DoubleIlaFactoryFuzzerSupport.consumeGetInput(data);

        final double[] left = DoubleIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final double[] right = DoubleIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final DoubleIlaFactory leftFactory = DoubleIlaFactoryFromArray.create(left);

        final DoubleIlaFactory rightFactory = DoubleIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final DoubleIlaFactory addFactory = DoubleIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        DoubleIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory, int length)
            throws Exception {

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final DoubleIlaFactory throwingLengthDoubleIlaFactory =
                () -> DoubleIlaFactoryFuzzerSupport.createIlaWithLengthException();

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryAdd.create(throwingLengthDoubleIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryAdd.create(leftFactory, throwingLengthDoubleIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final DoubleIlaFactory invalidBufferFactory = DoubleIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == DoubleIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final DoubleIlaFactory differentLengthFactory =
                DoubleIlaFactoryFromArray.create(DoubleIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final DoubleIlaFactory invalidLenFactory = DoubleIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

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

    private static double expectedValue(double left, double right) {

        return left + right;
    }
}
// AUTO GENERATED FROM TEMPLATE
