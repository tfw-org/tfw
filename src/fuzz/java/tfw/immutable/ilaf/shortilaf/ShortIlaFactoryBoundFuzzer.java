package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ShortIlaFactoryBoundFuzzer {

    private ShortIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ShortIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ShortIlaFactoryFuzzerSupport.GetInput input = ShortIlaFactoryFuzzerSupport.consumeGetInput(data);

        final short[] source = ShortIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ShortIlaFactory sourceIlaFactory = ShortIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final short minimum = consumeBound(data);
        final short maximum = consumeBound(data);

        final short actualMinimum;
        final short actualMaximum;

        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }

        final ShortIlaFactory boundedIlaFactory =
                ShortIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        ShortIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(ShortIlaFactory sourceFactory) throws Exception {

        ShortIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ShortIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final ShortIlaFactory invalidBoundsFactory =
                ShortIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static short consumeBound(FuzzedDataProvider data) {

        return (short) data.consumeInt();
    }

    private static short minimumValue() {

        return Short.MIN_VALUE;
    }

    private static short maximumValue() {

        return Short.MAX_VALUE;
    }

    private static short bound(short value, short minimum, short maximum) {

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
