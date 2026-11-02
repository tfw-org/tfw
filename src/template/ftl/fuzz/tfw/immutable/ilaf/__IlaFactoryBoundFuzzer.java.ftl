// byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ${NAME}IlaFactoryBoundFuzzer {

    private ${NAME}IlaFactoryBoundFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {

        final int length = data.consumeInt(0, ${NAME}IlaFactoryFuzzerSupport.MAX_LENGTH);

        final ${NAME}IlaFactoryFuzzerSupport.GetInput input = ${NAME}IlaFactoryFuzzerSupport.consumeGetInput(data);

        final ${TYPE}[] source = ${NAME}IlaFactoryFuzzerSupport.createInitializedArray(length, data);

        final ${NAME}IlaFactory sourceIlaFactory = ${NAME}IlaFactoryFromArray.create(source);

        testArgumentValidation(sourceIlaFactory);

        final ${TYPE} minimum = consumeBound(data);
        final ${TYPE} maximum = consumeBound(data);

        final ${TYPE} actualMinimum;
        final ${TYPE} actualMaximum;

        <#if TYPE == "float" || TYPE == "double">
        actualMinimum = Math.min(minimum, maximum);
        actualMaximum = Math.max(minimum, maximum);
        <#else>
        if (minimum <= maximum) {
            actualMinimum = minimum;
            actualMaximum = maximum;
        } else {
            actualMinimum = maximum;
            actualMaximum = minimum;
        }
        </#if>

        final ${NAME}IlaFactory boundedIlaFactory =
                ${NAME}IlaFactoryBound.create(sourceIlaFactory, actualMinimum, actualMaximum);

        ${NAME}IlaFactoryFuzzerSupport.verifyIla(
                boundedIlaFactory.create(),
                length,
                input,
                index -> bound(source[index], actualMinimum, actualMaximum),
                data);
    }

    private static void testArgumentValidation(${NAME}IlaFactory sourceFactory) throws Exception {

        ${NAME}IlaFactoryFuzzerSupport.expectIllegalArgumentException(
                () -> ${NAME}IlaFactoryBound.create(null, minimumValue(), maximumValue()),
                "create() accepted null ilaFactory");

        final ${NAME}IlaFactory invalidBoundsFactory =
                ${NAME}IlaFactoryBound.create(sourceFactory, maximumValue(), minimumValue());

        try {
            invalidBoundsFactory.create();

            throw new AssertionError("create() accepted minimum > maximum");

        } catch (IllegalArgumentException expected) {
            // Correct.
        }
    }

    private static ${TYPE} consumeBound(FuzzedDataProvider data) {

        <#if TYPE == "byte">
        return (byte) data.consumeInt();
        <#elseif TYPE == "char">
        return (char) data.consumeInt();
        <#elseif TYPE == "short">
        return (short) data.consumeInt();
        <#elseif TYPE == "int">
        return data.consumeInt();
        <#elseif TYPE == "long">
        return data.consumeLong();
        <#elseif TYPE == "float">
        final float value = data.consumeFloat();
        return Float.isNaN(value) ? 0.0f : value;
        <#elseif TYPE == "double">
        final double value = data.consumeDouble();
        return Double.isNaN(value) ? 0.0d : value;
        </#if>
    }

    private static ${TYPE} minimumValue() {

        <#if TYPE == "byte">
        return Byte.MIN_VALUE;
        <#elseif TYPE == "char">
        return Character.MIN_VALUE;
        <#elseif TYPE == "short">
        return Short.MIN_VALUE;
        <#elseif TYPE == "int">
        return Integer.MIN_VALUE;
        <#elseif TYPE == "long">
        return Long.MIN_VALUE;
        <#elseif TYPE == "float">
        return -Float.MAX_VALUE;
        <#elseif TYPE == "double">
        return -Double.MAX_VALUE;
        </#if>
    }

    private static ${TYPE} maximumValue() {

        <#if TYPE == "byte">
        return Byte.MAX_VALUE;
        <#elseif TYPE == "char">
        return Character.MAX_VALUE;
        <#elseif TYPE == "short">
        return Short.MAX_VALUE;
        <#elseif TYPE == "int">
        return Integer.MAX_VALUE;
        <#elseif TYPE == "long">
        return Long.MAX_VALUE;
        <#elseif TYPE == "float">
        return Float.MAX_VALUE;
        <#elseif TYPE == "double">
        return Double.MAX_VALUE;
        </#if>
    }

    private static ${TYPE} bound(${TYPE} value, ${TYPE} minimum, ${TYPE} maximum) {

        if (value < minimum) {
            return minimum;
        }

        if (value > maximum) {
            return maximum;
        }

        return value;
    }
}
