package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class LongIlaFactoryAddFuzzer {

    private LongIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final LongIlaFactoryFuzzerSupport.GetInput input = LongIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long[] left = LongIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final long[] right = LongIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final LongIlaFactory leftFactory = LongIlaFactoryFromArray.create(left);

        final LongIlaFactory rightFactory = LongIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final LongIlaFactory addFactory = LongIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        LongIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(LongIlaFactory leftFactory, LongIlaFactory rightFactory, int length)
            throws Exception {

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final LongIlaFactory throwingLengthLongIlaFactory =
                () -> LongIlaFactoryFuzzerSupport.createIlaWithLengthException();

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryAdd.create(throwingLengthLongIlaFactory, rightFactory, 1)
                        .create(),
                "create() accepted left ILA whose length() throws IOException");

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryAdd.create(leftFactory, throwingLengthLongIlaFactory, 1)
                        .create(),
                "create() accepted right ILA whose length() throws IOException");

        final LongIlaFactory invalidBufferFactory = LongIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == LongIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final LongIlaFactory differentLengthFactory =
                LongIlaFactoryFromArray.create(LongIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final LongIlaFactory invalidLenFactory = LongIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

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

    private static long expectedValue(long left, long right) {

        return left + right;
    }
}
// AUTO GENERATED FROM TEMPLATE
