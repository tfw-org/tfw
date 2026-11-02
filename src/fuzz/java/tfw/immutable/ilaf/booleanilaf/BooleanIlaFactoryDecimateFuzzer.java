package tfw.immutable.ilaf.booleanilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlaFactoryDecimateFuzzer {

    private BooleanIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, BooleanIlaFactoryFuzzerSupport.MAX_LENGTH);

        final BooleanIlaFactoryFuzzerSupport.GetInput input = BooleanIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, BooleanIlaFactoryFuzzerSupport.MAX_LENGTH);

        final boolean[] source = BooleanIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final boolean[] buffer = BooleanIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final BooleanIlaFactory sourceFactory = BooleanIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final BooleanIlaFactory decimateFactory = BooleanIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final BooleanIla ila;

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

        BooleanIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(BooleanIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        BooleanIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> BooleanIlaFactoryDecimate.create(null, 2, new boolean[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final BooleanIlaFactory invalidFactorIlaFactory =
                    BooleanIlaFactoryDecimate.create(sourceFactory, factor, new boolean[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final BooleanIlaFactory invalidFactory = BooleanIlaFactoryDecimate.create(sourceFactory, 2, new boolean[0]);

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
