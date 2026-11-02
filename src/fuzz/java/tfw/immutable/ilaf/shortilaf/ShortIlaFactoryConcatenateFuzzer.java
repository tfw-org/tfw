package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIlaFactoryConcatenateFuzzer {

    private ShortIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ShortIlaFactoryFuzzerSupport.GetInput input = ShortIlaFactoryFuzzerSupport.consumeGetInput(data);

        final short[] left = ShortIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final short[] right = ShortIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final ShortIlaFactory leftFactory = ShortIlaFactoryFromArray.create(left);

        final ShortIlaFactory rightFactory = ShortIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final ShortIlaFactory concatenateFactory = ShortIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        ShortIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final ShortIlaFactory leftFactory, ShortIlaFactory rightFactory)
            throws Exception {

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(ShortIlaFactory concatenateFactory, short[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final ShortIla ila = concatenateFactory.create();

        try {
            final short[] destination = new short[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            ShortIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
