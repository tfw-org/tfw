package tfw.immutable.ilaf.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<String[], ObjectIla<String>> SPEC = new IlaFuzzSpec<>(
            "ObjectIlaFactoryFromArray",
            new IlaArrayAdapter<>() {

                @Override
                public String[] create(int length) {
                    return new String[length];
                }

                @Override
                public void initialize(String[] array) {

                    for (int i = 0; i < array.length; i++) {

                        switch (i & 3) {
                            case 0:
                                array[i] = null;
                                break;

                            case 1:
                                array[i] = "tfw-" + i;
                                break;

                            case 2:
                                array[i] = "value-" + i + "-distinct";
                                break;

                            default:
                                array[i] = String.valueOf(Integer.MIN_VALUE + i);
                                break;
                        }
                    }
                }

                @Override
                public String[] copy(String[] array) {

                    return array.clone();
                }

                @Override
                public void assertElementEquals(
                        String[] expected, int expectedIndex, String[] actual, int actualIndex) {

                    String expectedValue = expected[expectedIndex];

                    String actualValue = actual[actualIndex];

                    if (expectedValue == null ? actualValue != null : !expectedValue.equals(actualValue)) {
                        throw new AssertionError("expected=" + expectedValue + ", actual=" + actualValue);
                    }
                }
            },
            array -> ObjectIlaFactoryFromArray.<String>create(array).create(),
            ObjectIla::length,
            ObjectIla::get);

    private static final IlaFuzzHarness<String[], ObjectIla<String>> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ObjectIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
