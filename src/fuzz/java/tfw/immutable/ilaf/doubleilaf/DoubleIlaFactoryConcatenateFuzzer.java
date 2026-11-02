package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIlaFactoryConcatenateFuzzer {

    private DoubleIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final DoubleIlaFactoryFuzzerSupport.GetInput input = DoubleIlaFactoryFuzzerSupport.consumeGetInput(data);

        final double[] left = DoubleIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final double[] right = DoubleIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final DoubleIlaFactory leftFactory = DoubleIlaFactoryFromArray.create(left);

        final DoubleIlaFactory rightFactory = DoubleIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final DoubleIlaFactory concatenateFactory = DoubleIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        DoubleIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final DoubleIlaFactory leftFactory, DoubleIlaFactory rightFactory)
            throws Exception {

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(DoubleIlaFactory concatenateFactory, double[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final DoubleIla ila = concatenateFactory.create();

        try {
            final double[] destination = new double[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            DoubleIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
