package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ByteIlaFactoryDivideFuzzer {

    private ByteIlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final ByteIlaFactoryFuzzerSupport.GetInput input = ByteIlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, ByteIlaFactoryFuzzerSupport.MAX_LENGTH);

        final byte[] left = ByteIlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final byte[] right = createDivisorArray(length, data);

        final ByteIlaFactory leftFactory = ByteIlaFactoryFromArray.create(left);

        final ByteIlaFactory rightFactory = ByteIlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ByteIlaFactory divideFactory = ByteIlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        ByteIlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(ByteIlaFactory leftFactory, ByteIlaFactory rightFactory, int length)
            throws Exception {

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ByteIlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ByteIlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ByteIlaFactory invalidBufferFactory = ByteIlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ByteIlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final byte[] differentLengthArray = ByteIlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final ByteIlaFactory differentLengthFactory = ByteIlaFactoryFromArray.create(differentLengthArray);

        final ByteIlaFactory invalidLengthIlaFactory =
                ByteIlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

        try {
            invalidLengthIlaFactory.create();

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

    private static byte[] createDivisorArray(int length, FuzzedDataProvider data) {

        final byte[] array = new byte[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    array[i] = 1;
                    break;

                case 1:
                    array[i] = -1;
                    break;

                case 2:
                    array[i] = 2;
                    break;

                case 3:
                    array[i] = -1;
                    break;

                case 4:
                    array[i] = Byte.MIN_VALUE;
                    break;

                case 5:
                    array[i] = Byte.MAX_VALUE;
                    break;

                default:
                    int value = data.consumeInt();
                    array[i] = (byte) (value == 0 ? 1 : value);
                    break;
            }
        }

        return array;
    }

    private static byte expectedValue(byte left, byte right) {

        return (byte) (left / right);
    }
}
// AUTO GENERATED FROM TEMPLATE
