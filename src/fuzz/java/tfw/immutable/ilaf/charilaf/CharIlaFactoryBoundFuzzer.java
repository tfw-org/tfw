package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class CharIlaFactoryBoundFuzzer {

    private CharIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, CharIlaFactoryFuzzerSupport.MAX_LENGTH);

        final CharIlaFactoryFuzzerSupport.GetInput input = CharIlaFactoryFuzzerSupport.consumeGetInput(data);

        final char[] source = CharIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final CharIlaFactory sourceIlaFactory = CharIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final char minimum = consumeBound(data);
        final char maximum = consumeBound(data);

        final char actualMinimum;
        final char actualMaximum;

        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }

        final CharIlaFactory boundedIlaFactory =
                CharIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        CharIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(CharIlaFactory sourceFactory) throws Exception {

        CharIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> CharIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final CharIlaFactory invalidBoundsFactory =
                CharIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static char consumeBound(FuzzedDataProvider data) {

        return (char) data.consumeInt();
    }

    private static char minimumValue() {

        return Character.MIN_VALUE;
    }

    private static char maximumValue() {

        return Character.MAX_VALUE;
    }

    private static char bound(char value, char minimum, char maximum) {

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
