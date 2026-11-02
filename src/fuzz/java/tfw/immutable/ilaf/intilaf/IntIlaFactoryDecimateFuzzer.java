package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryDecimateFuzzer {

    private IntIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final int[] source = IntIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final int[] buffer = IntIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final IntIlaFactory sourceFactory = IntIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final IntIlaFactory decimateFactory = IntIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final IntIla ila;

        try {
            ila = decimateFactory.create();

        } catch (IllegalArgumentException e) {
            throw new AssertionError(
                    "create() rejected valid arguments"
                            + " [sourceLength="
                            + sourceLength
                            + ", factor="
                            + factor
                            + ", bufferLength="
                            + bufferLength
                            + "]",
                    e);
        }

        final long expectedLength = expectedLength(sourceLength, factor);

        IntIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(IntIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryDecimate.create(null, 2, new int[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final IntIlaFactory invalidFactorIlaFactory =
                    IntIlaFactoryDecimate.create(sourceFactory, factor, new int[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final IntIlaFactory invalidFactory = IntIlaFactoryDecimate.create(sourceFactory, 2, new int[0]);

            try {
                invalidFactory.create();

                throw new AssertionError("create() accepted bufferLength=0");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }
    }

    private static long expectedLength(int sourceLength, long factor) {

        /*
         * Avoid the overflowing expression:
         *
         *     (sourceLength + factor - 1) / factor
         *
         * This equivalent form also handles very large factors.
         */
        if (sourceLength == 0) {
            return 0;
        }

        return 1 + (sourceLength - 1L) / factor;
    }
}
// AUTO GENERATED FROM TEMPLATE
