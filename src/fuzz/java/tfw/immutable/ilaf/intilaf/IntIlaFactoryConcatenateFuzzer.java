package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryConcatenateFuzzer {

    private IntIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int[] left = IntIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final int[] right = IntIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final IntIlaFactory leftFactory = IntIlaFactoryFromArray.create(left);

        final IntIlaFactory rightFactory = IntIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final IntIlaFactory concatenateFactory = IntIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        IntIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(IntIlaFactory leftFactory, IntIlaFactory rightFactory) throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(IntIlaFactory concatenateFactory, int[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final IntIla ila = concatenateFactory.create();

        try {
            final int[] destination = new int[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            IntIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
