package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIlaFactoryDecimateFuzzer {

    private DoubleIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final DoubleIlaFactoryFuzzerSupport.GetInput input = DoubleIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final double[] source = DoubleIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final double[] buffer = DoubleIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final DoubleIlaFactory sourceFactory = DoubleIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final DoubleIlaFactory decimateFactory = DoubleIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final DoubleIla ila;

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

        DoubleIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(DoubleIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryDecimate.create(null, 2, new double[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final DoubleIlaFactory invalidFactorIlaFactory =
                    DoubleIlaFactoryDecimate.create(sourceFactory, factor, new double[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final DoubleIlaFactory invalidFactory = DoubleIlaFactoryDecimate.create(sourceFactory, 2, new double[0]);

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
