// intilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ${NAME}IlaFactoryBoundFuzzer {

    private ${NAME}IlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final ${TYPE}[] source = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${NAME}IlaFactory sourceFactory = ${NAME}IlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory);

        final int minimum = data.consumeInt();
        final int maximum = data.consumeInt();

        final int actualMinimum = Math.min(minimum, maximum);
        final int actualMaximum = Math.max(minimum, maximum);

        final ${NAME}IlaFactory boundedFactory = ${NAME}IlaFactoryBound.create(sourceFactory, actualMinimum, actualMaximum);

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                boundedFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory sourceFactory) throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryBound.create(null, 0, 1), "create() accepted null ilaFactory");

        final ${NAME}IlaFactory invalidBoundsFactory = ${NAME}IlaFactoryBound.create(sourceFactory, 1, 0);

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static int bound(int value, int minimum, int maximum) {

        if (value < minimum) {
            return minimum;
        }

        if (value > maximum) {
            return maximum;
        }

        return value;
    }
}
