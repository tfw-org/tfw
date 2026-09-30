package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.longila.LongIla;

public final class LongIlaFactoryConcatenateFuzzer {

    private LongIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final LongIlaFactoryFuzzerSupport.GetInput input = LongIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long[] left = LongIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final long[] right = LongIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final LongIlaFactory leftFactory = LongIlaFactoryFromArray.create(left);

        final LongIlaFactory rightFactory = LongIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final LongIlaFactory concatenateFactory = LongIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        LongIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final LongIlaFactory leftFactory, LongIlaFactory rightFactory)
            throws Exception {

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(LongIlaFactory concatenateFactory, long[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final LongIla ila = concatenateFactory.create();

        try {
            final long[] destination = new long[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            LongIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
