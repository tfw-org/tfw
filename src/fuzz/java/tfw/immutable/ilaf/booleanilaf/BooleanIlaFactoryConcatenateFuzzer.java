package tfw.immutable.ilaf.booleanilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlaFactoryConcatenateFuzzer {

    private BooleanIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, BooleanIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, BooleanIlaFactoryFuzzerSupport.MAX_LENGTH);

        final BooleanIlaFactoryFuzzerSupport.GetInput input = BooleanIlaFactoryFuzzerSupport.consumeGetInput(data);

        final boolean[] left = BooleanIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final boolean[] right = BooleanIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final BooleanIlaFactory leftFactory = BooleanIlaFactoryFromArray.create(left);

        final BooleanIlaFactory rightFactory = BooleanIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final BooleanIlaFactory concatenateFactory = BooleanIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        BooleanIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final BooleanIlaFactory leftFactory, BooleanIlaFactory rightFactory)
            throws Exception {

        BooleanIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> BooleanIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        BooleanIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> BooleanIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(BooleanIlaFactory concatenateFactory, boolean[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final BooleanIla ila = concatenateFactory.create();

        try {
            final boolean[] destination = new boolean[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            BooleanIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
