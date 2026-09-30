package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIlaFactoryDecimateFuzzer {

    private FloatIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final FloatIlaFactoryFuzzerSupport.GetInput input = FloatIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final float[] source = FloatIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final float[] buffer = FloatIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final FloatIlaFactory sourceFactory = FloatIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final FloatIlaFactory decimateFactory = FloatIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final FloatIla ila;

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

        FloatIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(FloatIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryDecimate.create(null, 2, new float[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final FloatIlaFactory invalidFactorIlaFactory =
                    FloatIlaFactoryDecimate.create(sourceFactory, factor, new float[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final FloatIlaFactory invalidFactory = FloatIlaFactoryDecimate.create(sourceFactory, 2, new float[0]);

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
