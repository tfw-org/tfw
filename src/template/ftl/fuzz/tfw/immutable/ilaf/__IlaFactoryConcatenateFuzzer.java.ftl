// intilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.immutable.ila.${LOWERCASE}ila.${NAME}Ila;

public final class ${NAME}IlaFactoryConcatenateFuzzer {

    private ${NAME}IlaFactoryConcatenateFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int leftLength = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final int rightLength = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final ${TYPE}[] left = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(leftLength, data);

        final ${TYPE}[] right = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(rightLength, data);

        final ${NAME}IlaFactory leftFactory = ${NAME}IlaFactoryFromArray.create(left);

        final ${NAME}IlaFactory rightFactory = ${NAME}IlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory);

        final ${NAME}IlaFactory concatenateFactory = ${NAME}IlaFactoryConcatenate.create(leftFactory, rightFactory);

        final int expectedLength = leftLength + rightLength;

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                concatenateFactory.create(),
                expectedLength,
                input,
                index -> index < leftLength ? left[index] : right[index - leftLength],
                data);

        testLeftBoundary(concatenateFactory, left, leftLength);
    }

    private static void testArgumentValidation(${NAME}IlaFactory leftFactory, ${NAME}IlaFactory rightFactory) throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryConcatenate.create(null, rightFactory), "create() accepted null leftFactory");

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryConcatenate.create(leftFactory, null), "create() accepted null rightFactory");
    }

    private static void testLeftBoundary(${NAME}IlaFactory concatenateFactory, ${TYPE}[] left, int leftLength)
            throws Exception {

        if (leftLength == 0) {
            return;
        }

        final ${NAME}Ila ila = concatenateFactory.create();

        try {
            final ${TYPE}[] destination = new ${TYPE}[1];

            /*
             * Read exactly the final element of the left ILA.
             *
             * This specifically exercises the boundary where the
             * right side has zero elements to contribute.
             */
            ila.get(destination, 0, leftLength - 1L, 1);

            ${NAME}IlaFactoryFuzzerSupport.assertEquals(left[leftLength - 1], destination[0], leftLength - 1);

        } finally {
            ila.close();
        }
    }
}
