// booleanilaf,byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,objectilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.${LOWERCASE}ila.${NAME}Ila;

public final class ${NAME}IlaFactoryDecimateFuzzer {

    private ${NAME}IlaFactoryDecimateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int sourceLength = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final long factor = data.consumeLong();

        final int bufferLength = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${TYPE}[] source = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(sourceLength, data);

        final ${TYPE}[] buffer = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(bufferLength, data);

        final ${NAME}IlaFactory sourceFactory = ${NAME}IlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory, factor, bufferLength);

        if (factor < 2 || bufferLength < 1) {
            return;
        }

        final ${NAME}IlaFactory decimateFactory = ${NAME}IlaFactoryDecimate.create(sourceFactory, factor, buffer);

        final ${NAME}Ila ila;

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

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                ila, expectedLength, input, index -> source[Math.toIntExact(index * factor)], data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory sourceFactory, long factor, int bufferLength)
            throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryDecimate.create(null, 2, new ${TYPE}[1]), "create() accepted null ilaFactory");

        if (factor < 2) {
            final ${NAME}IlaFactory invalidFactorIlaFactory =
                    ${NAME}IlaFactoryDecimate.create(sourceFactory, factor, new ${TYPE}[1]);

            try {
                invalidFactorIlaFactory.create();

                throw new AssertionError("create() accepted invalid factor" + " [factor=" + factor + "]");

            } catch (IllegalArgumentException expected) {
                // Correct.
            }
        }

        if (bufferLength == 0) {
            final ${NAME}IlaFactory invalidFactory = ${NAME}IlaFactoryDecimate.create(sourceFactory, 2, new ${TYPE}[0]);

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
