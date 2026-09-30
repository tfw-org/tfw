package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class DoubleIlaFactoryBoundFuzzer {

    private DoubleIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, DoubleIlaFactoryFuzzerSupport.MAX_LENGTH);

        final DoubleIlaFactoryFuzzerSupport.GetInput input = DoubleIlaFactoryFuzzerSupport.consumeGetInput(data);

        final double[] source = DoubleIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final DoubleIlaFactory sourceIlaFactory = DoubleIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final double minimum = consumeBound(data);
        final double maximum = consumeBound(data);

        final double actualMinimum;
        final double actualMaximum;

        actualMinimum = Math.min(minimum, maximum);
        actualMaximum = Math.max(minimum, maximum);

        final DoubleIlaFactory boundedIlaFactory =
                DoubleIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        DoubleIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(DoubleIlaFactory sourceFactory) throws Exception {

        DoubleIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> DoubleIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final DoubleIlaFactory invalidBoundsFactory =
                DoubleIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static double consumeBound(FuzzedDataProvider data) {

        final double value = data.consumeDouble();
        return Double.isNaN(value) ? 0.0d : value;
    }

    private static double minimumValue() {

        return -Double.MAX_VALUE;
    }

    private static double maximumValue() {

        return Double.MAX_VALUE;
    }

    private static double bound(double value, double minimum, double maximum) {

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
