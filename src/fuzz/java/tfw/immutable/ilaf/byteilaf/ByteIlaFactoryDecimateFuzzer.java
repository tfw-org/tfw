package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIlaFactoryDecimateFuzzer {

    private ByteIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ByteIlaFactoryFuzzerSupport.GetInput input = ByteIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final byte[] source = ByteIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final byte[] buffer = ByteIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final ByteIlaFactory sourceFactory = ByteIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final ByteIlaFactory decimateFactory = ByteIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final ByteIla ila;

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

        ByteIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(ByteIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryDecimate.create(null, 2, new byte[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final ByteIlaFactory invalidFactorIlaFactory =
                    ByteIlaFactoryDecimate.create(sourceFactory, factor, new byte[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final ByteIlaFactory invalidFactory = ByteIlaFactoryDecimate.create(sourceFactory, 2, new byte[0]);

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
