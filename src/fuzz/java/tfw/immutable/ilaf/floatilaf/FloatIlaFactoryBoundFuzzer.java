package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class FloatIlaFactoryBoundFuzzer {

    private FloatIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, FloatIlaFactoryFuzzerSupport.MAX_LENGTH);

        final FloatIlaFactoryFuzzerSupport.GetInput input = FloatIlaFactoryFuzzerSupport.consumeGetInput(data);

        final float[] source = FloatIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final FloatIlaFactory sourceIlaFactory = FloatIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final float minimum = consumeBound(data);
        final float maximum = consumeBound(data);

        final float actualMinimum;
        final float actualMaximum;

        actualMinimum = Math.min(minimum, maximum);
        actualMaximum = Math.max(minimum, maximum);

        final FloatIlaFactory boundedIlaFactory =
                FloatIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        FloatIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(FloatIlaFactory sourceFactory) throws Exception {

        FloatIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> FloatIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final FloatIlaFactory invalidBoundsFactory =
                FloatIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static float consumeBound(FuzzedDataProvider data) {

        final float value = data.consumeFloat();
        return Float.isNaN(value) ? 0.0f : value;
    }

    private static float minimumValue() {

        return -Float.MAX_VALUE;
    }

    private static float maximumValue() {

        return Float.MAX_VALUE;
    }

    private static float bound(float value, float minimum, float maximum) {

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
