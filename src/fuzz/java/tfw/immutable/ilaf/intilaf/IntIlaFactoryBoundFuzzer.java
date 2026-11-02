package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class IntIlaFactoryBoundFuzzer {

    private IntIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, IntIlaFactoryFuzzerSupport.MAX_LENGTH);

        final IntIlaFactoryFuzzerSupport.GetInput input = IntIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int[] source = IntIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final IntIlaFactory sourceIlaFactory = IntIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final int minimum = consumeBound(data);
        final int maximum = consumeBound(data);

        final int actualMinimum;
        final int actualMaximum;

        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }

        final IntIlaFactory boundedIlaFactory =
                IntIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        IntIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(IntIlaFactory sourceFactory) throws Exception {

        IntIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> IntIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final IntIlaFactory invalidBoundsFactory =
                IntIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static int consumeBound(FuzzedDataProvider data) {

        return data.consumeInt();
    }

    private static int minimumValue() {

        return Integer.MIN_VALUE;
    }

    private static int maximumValue() {

        return Integer.MAX_VALUE;
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
// AUTO GENERATED FROM TEMPLATE
