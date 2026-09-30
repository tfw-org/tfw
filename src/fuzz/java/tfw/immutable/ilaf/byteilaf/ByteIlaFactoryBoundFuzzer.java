package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ByteIlaFactoryBoundFuzzer {

    private ByteIlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ByteIlaFactoryFuzzerSupport.GetInput input = ByteIlaFactoryFuzzerSupport.consumeGetInput(data);

        final byte[] source = ByteIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ByteIlaFactory sourceIlaFactory = ByteIlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final byte minimum = consumeBound(data);
        final byte maximum = consumeBound(data);

        final byte actualMinimum;
        final byte actualMaximum;

        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }

        final ByteIlaFactory boundedIlaFactory =
                ByteIlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        ByteIlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(ByteIlaFactory sourceFactory) throws Exception {

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final ByteIlaFactory invalidBoundsFactory =
                ByteIlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static byte consumeBound(FuzzedDataProvider data) {

        return (byte) data.consumeInt();
    }

    private static byte minimumValue() {

        return Byte.MIN_VALUE;
    }

    private static byte maximumValue() {

        return Byte.MAX_VALUE;
    }

    private static byte bound(byte value, byte minimum, byte maximum) {

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
