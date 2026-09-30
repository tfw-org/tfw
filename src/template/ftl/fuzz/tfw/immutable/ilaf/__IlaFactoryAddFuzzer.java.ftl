// byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ${NAME}IlaFactoryAddFuzzer {

    private ${NAME}IlaFactoryAddFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final ${TYPE}[] left = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${TYPE}[] right = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${NAME}IlaFactory leftFactory = ${NAME}IlaFactoryFromArray.create(left);

        final ${NAME}IlaFactory rightFactory = ${NAME}IlaFactoryFromArray.create(right);

        testArgumentValidation(leftFactory, rightFactory, length);

        final ${NAME}IlaFactory addFactory = ${NAME}IlaFactoryAdd.create(leftFactory, rightFactory, 1);

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                addFactory.create(), length, input, index -> expectedValue(left[index], right[index]), data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory leftFactory, ${NAME}IlaFactory rightFactory, int length)
            throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryAdd.create(null, rightFactory, 1), "create() accepted null leftFactory");

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryAdd.create(leftFactory, null, 1), "create() accepted null rightFactory");

        final ${NAME}IlaFactory invalidBufferFactory = ${NAME}IlaFactoryAdd.create(leftFactory, rightFactory, 0);

        try {
            invalidBufferFactory.create();

            throw new AssertionError("create() accepted bufferSize=0");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }

        final int differentLength = length == ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH ? length - 1 : length + 1;

        final ${NAME}IlaFactory differentLengthFactory =
                ${NAME}IlaFactoryFromArray.create(${NAME}IlaFactoryFuzzerSupport.createInitializedArray(differentLength));

        final ${NAME}IlaFactory invalidLenFactory = ${NAME}IlaFactoryAdd.create(leftFactory, differentLengthFactory, 1);

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

    private static ${TYPE} expectedValue(${TYPE} left, ${TYPE} right) {

        <#if TYPE == "byte">
        return (byte) (left + right);
        <#elseif TYPE == "char">
        return (char) (left + right);
        <#elseif TYPE == "short">
        return (short) (left + right);
        <#else>
        return left + right;
        </#if>
    }
}
