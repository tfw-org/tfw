package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIlaFactoryConcatenateFuzzer {

    private FloatIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final FloatIlaFactoryFuzzerSupport.GetInput input = FloatIlaFactoryFuzzerSupport.consumeGetInput(data);

        final float[] left = FloatIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final float[] right = FloatIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final FloatIlaFactory leftFactory = FloatIlaFactoryFromArray.create(left);

        final FloatIlaFactory rightFactory = FloatIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final FloatIlaFactory concatenateFactory = FloatIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        FloatIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final FloatIlaFactory leftFactory, FloatIlaFactory rightFactory)
            throws Exception {

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(FloatIlaFactory concatenateFactory, float[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final FloatIla ila = concatenateFactory.create();

        try {
            final float[] destination = new float[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            FloatIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
