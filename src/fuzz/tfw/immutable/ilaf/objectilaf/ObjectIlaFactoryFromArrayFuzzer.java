package tfw.immutable.ilaf.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<Object[], ObjectIla<Object>> SPEC = new IlaFuzzSpec<>(
            "ObjectIlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public Object[] create(int length) {
                    return new Object[length];
                }

                @Override
                public void initialize(Object[] array) {
                    for (int i = 0; i < array.length; i++) {
                        switch (i & 3) {
                            case 0:
                                array[i] = null;
                                break;
                            case 1:
                                array[i] = "tfw-" + i;
                                break;
                            case 2:
                                array[i] = Integer.valueOf(i);
                                break;
                            default:
                                array[i] = Long.valueOf(i);
                                break;
                        }
                    }
                }

                @Override
                public Object[] copy(Object[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(
                        Object[] expected, int expectedIndex, Object[] actual, int actualIndex) {
                    Object expectedValue = expected[expectedIndex];
                    Object actualValue = actual[actualIndex];
                    if (expectedValue == null ? actualValue != null : !expectedValue.equals(actualValue)) {
                        throw new AssertionError("expected=" + expectedValue + ", actual=" + actualValue);
                    }
                }
            },
            array -> ObjectIlaFactoryFromArray.<Object>create(array).create(),
            ObjectIla::length,
            ObjectIla::get);

    private static final IlaFuzzHarness<Object[], ObjectIla<Object>> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ObjectIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
