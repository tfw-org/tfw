package ${package};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.${ilaPackage}.${ilaType};

public final class ${className} {
    private static final IlaFuzzSpec<${arrayType}, ${ilaType}<#if generic??><${generic}></#if>> SPEC = new IlaFuzzSpec<>(
            "${factoryName}",
            new IlaArrayAdapter<>() {
                @Override
                public ${arrayType} create(int length) {
                    return new ${elementType}[length];
                }

                @Override
                public void initialize(${arrayType} array) {
${initialize}
                }

                @Override
                public ${arrayType} copy(${arrayType} array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(<#if singleLineAssertElementEquals>${arrayType} expected, int expectedIndex, ${arrayType} actual, int actualIndex<#else>
                        ${arrayType} expected, int expectedIndex, ${arrayType} actual, int actualIndex</#if>) {
${assertElementEquals}
                }
            },
            ${createExpression},
            ${lengthExpression},
            ${getExpression});

    private static final IlaFuzzHarness<${arrayType}, ${ilaType}<#if generic??><${generic}></#if>> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ${className}() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
