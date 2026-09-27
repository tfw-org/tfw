package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class IntIlaFactoryBoundFuzzer {

    private IntIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int[] source = IntIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final IntIlaFactory sourceFactory = IntIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceFactory);

        final int minimum = data.consumeInt();
        final int maximum = data.consumeInt();

        final int actualMinimum = Math.min(minimum, maximum);
        final int actualMaximum = Math.max(minimum, maximum);

        final IntIlaFactory boundedFactory = IntIlaFactoryBound.create(sourceFactory, actualMinimum, actualMaximum);

        IntIlaFactoryFuzzerSupport.verifyIla(
                boundedFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(IntIlaFactory sourceFactory) throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryBound.create(null, 0, 1), "create() accepted null ilaFactory");

        final IntIlaFactory invalidBoundsFactory = IntIlaFactoryBound.create(sourceFactory, 1, 0);

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
