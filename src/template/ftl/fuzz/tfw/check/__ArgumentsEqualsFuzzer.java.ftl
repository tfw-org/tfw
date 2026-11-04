// objectilaf
package ${PACKAGE};

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.util.Objects;
import tfw.check.Arguments;
import tfw.check.ArgumentsFuzzerSupport;

public final class ${NAME}ArgumentsEqualsFuzzer {

    private ${NAME}ArgumentsEqualsFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        final String left = ArgumentsFuzzerSupport.consumeNullableString(data);
        final String right = ArgumentsFuzzerSupport.consumeNullableString(data);

        final boolean shouldThrow = !Objects.equals(left, right);

        if (data.consumeBoolean()) {
            ArgumentsFuzzerSupport.verify(
                    shouldThrow,
                    () -> Arguments.checkEquals(left, "left", right, "right"),
                    "checkEquals with four arguments");
        } else {
            ArgumentsFuzzerSupport.verify(
                    shouldThrow, () -> Arguments.checkEquals(left, "left", right), "checkEquals with three arguments");
        }
    }
}
