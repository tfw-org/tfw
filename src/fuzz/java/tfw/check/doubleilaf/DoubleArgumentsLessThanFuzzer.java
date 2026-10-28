package tfw.check.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.check.Arguments;
import tfw.check.ArgumentsFuzzerSupport;

public final class DoubleArgumentsLessThanFuzzer {

    private DoubleArgumentsLessThanFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        final double left = data.consumeDouble();
        final double right = data.consumeDouble();

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
