// intilaf,doubleilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.check.Arguments;
import tfw.check.ArgumentsFuzzerSupport;

public final class ${NAME}ArgumentsLessThanFuzzer {

    private ${NAME}ArgumentsLessThanFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        <#if TYPE == "int">
        final int left = data.consumeInt();
        final int right = data.consumeInt();
        <#else>
        final double left = data.consumeDouble();
        final double right = data.consumeDouble();
        </#if>

        final boolean shouldThrow = left >= right;

        if (data.consumeBoolean()) {
            ArgumentsFuzzerSupport.verify(
                    shouldThrow,
                    () -> Arguments.checkLessThan(left, "left", right, "right"),
                    "checkLessThan with four arguments");
        } else {
            ArgumentsFuzzerSupport.verify(
                    shouldThrow,
                    () -> Arguments.checkLessThan(left, "left", right),
                    "checkLessThan with three arguments");
        }
    }
}
