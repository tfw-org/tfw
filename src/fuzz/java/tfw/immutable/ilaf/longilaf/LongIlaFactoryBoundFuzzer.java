package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class LongIlaFactoryBoundFuzzer {

    private LongIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, LongIlaFactoryFuzzerSupport.MAX_LENGTH);

        final LongIlaFactoryFuzzerSupport.GetInput input = LongIlaFactoryFuzzerSupport.consumeGetInput(data);

        final long[] source = LongIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final LongIlaFactory sourceIlaFactory = LongIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final long minimum = consumeBound(data);
        final long maximum = consumeBound(data);

        final long actualMinimum;
        final long actualMaximum;

        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }

        final LongIlaFactory boundedIlaFactory =
                LongIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        LongIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(LongIlaFactory sourceFactory) throws Exception {

        LongIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> LongIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final LongIlaFactory invalidBoundsFactory =
                LongIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static long consumeBound(FuzzedDataProvider data) {

        return data.consumeLong();
    }

    private static long minimumValue() {

        return Long.MIN_VALUE;
    }

    private static long maximumValue() {

        return Long.MAX_VALUE;
    }

    private static long bound(long value, long minimum, long maximum) {

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
