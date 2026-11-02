package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIlaFactoryConcatenateFuzzer {

    private ByteIlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ByteIlaFactoryFuzzerSupport.GetInput input = ByteIlaFactoryFuzzerSupport.consumeGetInput(data);

        final byte[] left = ByteIlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final byte[] right = ByteIlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final ByteIlaFactory leftFactory = ByteIlaFactoryFromArray.create(left);

        final ByteIlaFactory rightFactory = ByteIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final ByteIlaFactory concatenateFactory = ByteIlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        ByteIlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(final ByteIlaFactory leftFactory, ByteIlaFactory rightFactory)
            throws Exception {

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(ByteIlaFactory concatenateFactory, byte[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final ByteIla ila = concatenateFactory.create();

        try {
            final byte[] destination = new byte[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            ByteIlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
