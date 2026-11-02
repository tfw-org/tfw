package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.longila.LongIla;

public final class LongIlaFactoryDecimateFuzzer {

    private LongIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final LongIlaFactoryFuzzerSupport.GetInput input = LongIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final long[] source = LongIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final long[] buffer = LongIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final LongIlaFactory sourceFactory = LongIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final LongIlaFactory decimateFactory = LongIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final LongIla ila;

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

        LongIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(LongIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryDecimate.create(null, 2, new long[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final LongIlaFactory invalidFactorIlaFactory =
                    LongIlaFactoryDecimate.create(sourceFactory, factor, new long[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final LongIlaFactory invalidFactory = LongIlaFactoryDecimate.create(sourceFactory, 2, new long[0]);

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
