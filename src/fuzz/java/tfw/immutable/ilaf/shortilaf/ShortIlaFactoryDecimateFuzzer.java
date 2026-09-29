package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIlaFactoryDecimateFuzzer {

    private ShortIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ShortIlaFactoryFuzzerSupport.GetInput input = ShortIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final short[] source = ShortIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final short[] buffer = ShortIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final ShortIlaFactory sourceFactory = ShortIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final ShortIlaFactory decimateFactory = ShortIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final ShortIla ila;

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

        ShortIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(ShortIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryDecimate.create(null, 2, new short[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final ShortIlaFactory invalidFactorIlaFactory =
                    ShortIlaFactoryDecimate.create(sourceFactory, factor, new short[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final ShortIlaFactory invalidFactory = ShortIlaFactoryDecimate.create(sourceFactory, 2, new short[0]);

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
