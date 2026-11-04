package tfw.check.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.check.Arguments;
import tfw.check.ArgumentsFuzzerSupport;

public final class ObjectArgumentsNotNullFuzzer {

    private ObjectArgumentsNotNullFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {

        final String value = ArgumentsFuzzerSupport.consumeNullableString(data);

        ArgumentsFuzzerSupport.verify(value == null, () -> Arguments.checkNotNull(value, "value"), "checkNotNull");
    }
}
// AUTO GENERATED FROM TEMPLATE
