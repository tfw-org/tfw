package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ShortIlaFactoryDivideFuzzer {

    private ShortIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ShortIlaFactoryFuzzerSupport.GetInput input = ShortIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final short[] left = ShortIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final short[] right = createDivisorArray(length, data);

        final ShortIlaFactory leftFactory = ShortIlaFactoryFromArray.create(left);

        final ShortIlaFactory rightFactory = ShortIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ShortIlaFactory divideFactory = ShortIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        ShortIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(ShortIlaFactory leftFactory, ShortIlaFactory rightFactory, int length)
            throws Exception {

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ShortIlaFactory throwingLengthShortIlaFactory =
                () -> ShortIlaFactoryFuzzerSupport.createIlaWithLengthException();

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryDivide.create(throwingLengthShortIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryDivide.create(leftFactory, throwingLengthShortIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final ShortIlaFactory invalidBufferFactory = ShortIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ShortIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final short[] differentLengthArray = ShortIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final ShortIlaFactory differentLengthFactory = ShortIlaFactoryFromArray.create(differentLengthArray);

        final ShortIlaFactory invalidLengthIlaFactory =
                ShortIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static short[] createDivisorArray(int length, FuzzedDataProvider data) {

        final short[] array = new short[length];

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
                    array[i] = -1;
                    break;

                case 4:
                    array[i] = Short.MIN_VALUE;
                    break;

                case 5:
                    array[i] = Short.MAX_VALUE;
                    break;

                default:
                    short value = (short) data.consumeInt();
                    array[i] = value == 0 ? 1 : value;
                    break;
            }
        }

        return array;
    }

    private static short expectedValue(short left, short right) {

        return (short) (left / right);
    }
}
// AUTO GENERATED FROM TEMPLATE
