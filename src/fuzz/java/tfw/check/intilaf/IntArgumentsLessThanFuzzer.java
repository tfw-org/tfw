package tfw.check.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.check.Arguments;
import tfw.check.ArgumentsFuzzerSupport;

public final class IntArgumentsLessThanFuzzer {

    private IntArgumentsLessThanFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        final int left = data.consumeInt();
        final int right = data.consumeInt();

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
// AUTO GENERATED FROM TEMPLATE
