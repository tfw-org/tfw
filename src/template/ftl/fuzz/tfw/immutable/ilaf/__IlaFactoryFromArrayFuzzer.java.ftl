// booleanilaf,byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,objectilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.${LOWERCASE}ila.${NAME}Ila;

public final class ${NAME}IlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<${TYPE}[], ${NAME}Ila<#if TYPE == "Object"><Object></#if>> SPEC = new IlaFuzzSpec<>(
            "${NAME}IlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public ${TYPE}[] create(int length) {
                    return new ${TYPE}[length];
                }

                @Override
                public void initialize(${TYPE}[] array) {
${FUZZ_INITIALIZE}
                }

                @Override
                public ${TYPE}[] copy(${TYPE}[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(<#if FUZZ_SINGLE_LINE_ASSERT_ELEMENT_EQUALS>${TYPE}[] expected, int expectedIndex, ${TYPE}[] actual, int actualIndex<#else>
                        ${TYPE}[] expected, int expectedIndex, ${TYPE}[] actual, int actualIndex</#if>) {
${FUZZ_ASSERT_ELEMENT_EQUALS}
                }
            },
            array -> ${NAME}IlaFactoryFromArray.<#if TYPE == "Object"><Object></#if>create(array).create(),
            ${NAME}Ila::length,
            ${NAME}Ila::get);

    private static final IlaFuzzHarness<${TYPE}[], ${NAME}Ila<#if TYPE == "Object"><Object></#if>> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ${NAME}IlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
