package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.charila.CharIla;

public final class CharIlaFactoryConcatenateFuzzer {

    private CharIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final CharIlaFactoryFuzzerSupport.GetInput input = CharIlaFactoryFuzzerSupport.consumeGetInput(data);

        final char[] left = CharIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final char[] right = CharIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final CharIlaFactory leftFactory = CharIlaFactoryFromArray.create(left);

        final CharIlaFactory rightFactory = CharIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final CharIlaFactory concatenateFactory = CharIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        CharIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final CharIlaFactory leftFactory, CharIlaFactory rightFactory)
            throws Exception {

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(CharIlaFactory concatenateFactory, char[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final CharIla ila = concatenateFactory.create();

        try {
            final char[] destination = new char[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            CharIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
