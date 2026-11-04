package tfw.check;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public final class ArgumentsFuzzerSupport {

    private ArgumentsFuzzerSupport() {}

    public static void verify(boolean shouldThrow, Runnable check, String description) {

        try {
            check.run();

        } catch (IllegalArgumentException expected) {

            if (!shouldThrow) {
                throw new AssertionError(description + ": threw unexpectedly", expected);
            }

            return;
        }

        if (shouldThrow) {
            throw new AssertionError(description + ": did not throw");
        }
    }

    public static String consumeNullableString(FuzzedDataProvider data) {
        return data.consumeBoolean() ? null : data.consumeString(100);
    }
}
