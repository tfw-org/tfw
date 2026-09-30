// byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ${NAME}IlaFactoryDivideFuzzer {

    private ${NAME}IlaFactoryDivideFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final int bufferSize = data.consumeInt(1, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${TYPE}[] left = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${TYPE}[] right = createDivisorArray(length, data);

        final ${NAME}IlaFactory leftFactory = ${NAME}IlaFactoryFromArray.create(left);

        final ${NAME}IlaFactory rightFactory = ${NAME}IlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ${NAME}IlaFactory divideFactory = ${NAME}IlaFactoryDivide.create(leftFactory, rightFactory, bufferSize);

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                divideFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory leftFactory, ${NAME}IlaFactory rightFactory, int length)
            throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryDivide.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryDivide.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ${NAME}IlaFactory invalidBufferFactory = ${NAME}IlaFactoryDivide.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final ${TYPE}[] differentLengthArray = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(differentLength);

        final ${NAME}IlaFactory differentLengthFactory = ${NAME}IlaFactoryFromArray.create(differentLengthArray);

        final ${NAME}IlaFactory invalidLengthIlaFactory =
                ${NAME}IlaFactoryDivide.create(leftFactory, differentLengthFactory, 1);

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

    private static ${TYPE}[] createDivisorArray(int length, FuzzedDataProvider data) {

        final ${TYPE}[] array = new ${TYPE}[length];

        for (int i = 0; i < array.length; i++) {
            switch (i & 7) {
                case 0:
                    <#if TYPE == "char">
                    array[i] = (char) 1;
                    <#elseif TYPE == "float">
                    array[i] = 0.0f;
                    <#elseif TYPE == "double">
                    array[i] = 0.0d;
                    <#else>
                    array[i] = 1;
                    </#if>
                    break;

                case 1:
                    <#if TYPE == "char">
                    array[i] = (char) 2;
                    <#elseif TYPE == "float">
                    array[i] = -0.0f;
                    <#elseif TYPE == "double">
                    array[i] = -0.0d;
                    <#else>
                    array[i] = -1;
                    </#if>
                    break;

                case 2:
                    <#if TYPE == "char">
                    array[i] = (char) 2;
                    <#elseif TYPE == "float">
                    array[i] = 1.0f;
                    <#elseif TYPE == "double">
                    array[i] = 1.0d;
                    <#else>
                    array[i] = 2;
                    </#if>
                    break;

                case 3:
                    <#if TYPE == "char">
                    array[i] = Character.MAX_VALUE;
                    <#elseif TYPE == "float">
                    array[i] = -1.0f;
                    <#elseif TYPE == "double">
                    array[i] = -1.0d;
                    <#else>
                    array[i] = -1;
                    </#if>
                    break;

                case 4:
                    <#if TYPE == "char">
                    array[i] = (char) 3;
                    <#elseif TYPE == "float">
                    array[i] = Float.MIN_VALUE;
                    <#elseif TYPE == "double">
                    array[i] = Double.MIN_VALUE;
                    <#elseif TYPE == "byte">
                    array[i] = Byte.MIN_VALUE;
                    <#elseif TYPE == "short">
                    array[i] = Short.MIN_VALUE;
                    <#elseif TYPE == "int">
                    array[i] = Integer.MIN_VALUE;
                    <#elseif TYPE == "long">
                    array[i] = Long.MIN_VALUE;
                    </#if>
                    break;

                case 5:
                    <#if TYPE == "char">
                    array[i] = (char) 4;
                    <#elseif TYPE == "float">
                    array[i] = Float.MAX_VALUE;
                    <#elseif TYPE == "double">
                    array[i] = Double.MAX_VALUE;
                    <#elseif TYPE == "byte">
                    array[i] = Byte.MAX_VALUE;
                    <#elseif TYPE == "short">
                    array[i] = Short.MAX_VALUE;
                    <#elseif TYPE == "int">
                    array[i] = Integer.MAX_VALUE;
                    <#elseif TYPE == "long">
                    array[i] = Long.MAX_VALUE;
                    </#if>
                    break;

                default:
                    <#if TYPE == "char">
                    int value = data.consumeInt(1, Character.MAX_VALUE);
                    array[i] = (char) value;
                    <#elseif TYPE == "float">
                    array[i] = data.consumeFloat();
                    <#elseif TYPE == "double">
                    array[i] = data.consumeDouble();
                    <#elseif TYPE == "byte">
                    int value = data.consumeInt();
                    array[i] = (byte) (value == 0 ? 1 : value);
                    <#elseif TYPE == "short">
                    int value = data.consumeInt();
                    array[i] = (short) (value == 0 ? 1 : value);
                    <#elseif TYPE == "int">
                    int value = data.consumeInt();
                    array[i] = value == 0 ? 1 : value;
                    <#elseif TYPE == "long">
                    long value = data.consumeLong();
                    array[i] = value == 0 ? 1 : value;
                    </#if>
                    break;
            }
        }

        return array;
    }

    private static ${TYPE} expectedValue(${TYPE} left, ${TYPE} right) {

        <#if TYPE == "byte">
        return (byte) (left / right);
        <#elseif TYPE == "char">
        return (char) (left / right);
        <#elseif TYPE == "short">
        return (short) (left / right);
        <#else>
        return left / right;
        </#if>
    }
}
