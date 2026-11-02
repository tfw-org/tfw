package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.charila.CharIla;

public final class CharIlaFactoryDecimateFuzzer {

    private CharIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final CharIlaFactoryFuzzerSupport.GetInput input = CharIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final char[] source = CharIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final char[] buffer = CharIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final CharIlaFactory sourceFactory = CharIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final CharIlaFactory decimateFactory = CharIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final CharIla ila;

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

        CharIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(CharIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryDecimate.create(null, 2, new char[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final CharIlaFactory invalidFactorIlaFactory =
                    CharIlaFactoryDecimate.create(sourceFactory, factor, new char[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final CharIlaFactory invalidFactory = CharIlaFactoryDecimate.create(sourceFactory, 2, new char[0]);

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
