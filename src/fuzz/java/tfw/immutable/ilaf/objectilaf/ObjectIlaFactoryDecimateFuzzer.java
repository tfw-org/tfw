package tfw.immutable.ilaf.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIlaFactoryDecimateFuzzer {

    private ObjectIlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, ObjectIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ObjectIlaFactoryFuzzerSupport.GetInput input = ObjectIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, ObjectIlaFactoryFuzzerSupport.MAX_LENGTH);

        final Object[] source = ObjectIlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final Object[] buffer = ObjectIlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final ObjectIlaFactory sourceFactory = ObjectIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final ObjectIlaFactory decimateFactory = ObjectIlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final ObjectIla ila;

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

        ObjectIlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(ObjectIlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        ObjectIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ObjectIlaFactoryDecimate.create(null, 2, new Object[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final ObjectIlaFactory invalidFactorIlaFactory =
                    ObjectIlaFactoryDecimate.create(sourceFactory, factor, new Object[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final ObjectIlaFactory invalidFactory = ObjectIlaFactoryDecimate.create(sourceFactory, 2, new Object[0]);

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
