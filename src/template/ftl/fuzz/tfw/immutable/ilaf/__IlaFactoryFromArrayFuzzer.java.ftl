// booleanilaf,byteilaf,charilaf,doubleilaf,floatilaf,intilaf,longilaf,objectilaf,shortilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.${FUZZ_ILA_PACKAGE}.${FUZZ_ILA_TYPE};

public final class ${NAME}IlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<${FUZZ_ARRAY_TYPE}, ${FUZZ_ILA_TYPE}<#if FUZZ_GENERIC??><${FUZZ_GENERIC}></#if>> SPEC = new IlaFuzzSpec<>(
            "${FUZZ_FACTORY_NAME}",
            new IlaArrayAdapter<>() {
                @Override
                public ${FUZZ_ARRAY_TYPE} create(int length) {
                    return new ${FUZZ_ELEMENT_TYPE}[length];
                }

                @Override
                public void initialize(${FUZZ_ARRAY_TYPE} array) {
${FUZZ_INITIALIZE}
                }

                @Override
                public ${FUZZ_ARRAY_TYPE} copy(${FUZZ_ARRAY_TYPE} array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(<#if FUZZ_SINGLE_LINE_ASSERT_ELEMENT_EQUALS>${FUZZ_ARRAY_TYPE} expected, int expectedIndex, ${FUZZ_ARRAY_TYPE} actual, int actualIndex<#else>
                        ${FUZZ_ARRAY_TYPE} expected, int expectedIndex, ${FUZZ_ARRAY_TYPE} actual, int actualIndex</#if>) {
${FUZZ_ASSERT_ELEMENT_EQUALS}
                }
            },
            ${FUZZ_CREATE_EXPRESSION},
            ${FUZZ_LENGTH_EXPRESSION},
            ${FUZZ_GET_EXPRESSION});

    private static final IlaFuzzHarness<${FUZZ_ARRAY_TYPE}, ${FUZZ_ILA_TYPE}<#if FUZZ_GENERIC??><${FUZZ_GENERIC}></#if>> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ${NAME}IlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
