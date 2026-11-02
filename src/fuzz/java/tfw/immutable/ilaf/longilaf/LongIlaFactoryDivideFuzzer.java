package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class LongIlaFactoryDivideFuzzer {

    private LongIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final LongIlaFactoryFuzzerSupport.GetInput input = LongIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final long[] left = LongIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final long[] right = createDivisorArray(length, data);

        final LongIlaFactory leftFactory = LongIlaFactoryFromArray.create(left);

        final LongIlaFactory rightFactory = LongIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final LongIlaFactory divideFactory = LongIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        LongIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(LongIlaFactory leftFactory, LongIlaFactory rightFactory, int length)
            throws Exception {

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final LongIlaFactory throwingLengthLongIlaFactory =
                () -> LongIlaFactoryFuzzerSupport.createIlaWithLengthException();

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryDivide.create(throwingLengthLongIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryDivide.create(leftFactory, throwingLengthLongIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final LongIlaFactory invalidBufferFactory = LongIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == LongIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final long[] differentLengthArray = LongIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final LongIlaFactory differentLengthFactory = LongIlaFactoryFromArray.create(differentLengthArray);

        final LongIlaFactory invalidLengthIlaFactory =
                LongIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static long[] createDivisorArray(int length, FuzzedDataProvider data) {

        final long[] array = new long[length];

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
                    array[i] = Long.MIN_VALUE;
                    break;

                case 5:
                    array[i] = Long.MAX_VALUE;
                    break;

                default:
                    long value = data.consumeLong();
                    array[i] = value == 0 ? 1 : value;
                    break;
            }
        }

        return array;
    }

    private static long expectedValue(long left, long right) {

        return left / right;
    }
}
// AUTO GENERATED FROM TEMPLATE
