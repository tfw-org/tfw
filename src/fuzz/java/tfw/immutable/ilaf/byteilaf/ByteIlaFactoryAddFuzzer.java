package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ByteIlaFactoryAddFuzzer {

    private ByteIlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ByteIlaFactoryFuzzerSupport.GetInput input = ByteIlaFactoryFuzzerSupport.consumeGetInput(data);

        final byte[] left = ByteIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final byte[] right = ByteIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ByteIlaFactory leftFactory = ByteIlaFactoryFromArray.create(left);

        final ByteIlaFactory rightFactory = ByteIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ByteIlaFactory addFactory = ByteIlaFactoryAdd.create(leftFactory, rightFactory, 1);

        ByteIlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory, int length)
            throws Exception {

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ByteIlaFactory invalidBufferFactory = ByteIlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ByteIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final ByteIlaFactory differentLengthFactory =
                ByteIlaFactoryFromArray.create(ByteIlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final ByteIlaFactory invalidLenFactory = ByteIlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

        try {
            invalidLenFactory.create();

            throw new AssertionError("create() accepted factories with different lengths"
                    + " [leftLength="
                    + length
                    + ", rightLength="
                    + differentLength
                    + "]");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static byte expectedValue(byte left, byte right) {

        return (byte) (left + right);
    }
}
// AUTO GENERATED FROM TEMPLATE
