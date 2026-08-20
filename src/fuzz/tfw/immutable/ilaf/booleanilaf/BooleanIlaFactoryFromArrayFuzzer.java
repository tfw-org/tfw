package tfw.immutable.ilaf.booleanilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<boolean[], BooleanIla> SPEC = new IlaFuzzSpec<>(
            "BooleanIlaFactoryFromArray",
            new IlaArrayAdapter<>() {

                @Override
                public boolean[] create(int length) {
                    return new boolean[length];
                }

                @Override
                public void initialize(boolean[] array) {

                    for (int i = 0; i < array.length; i++) {
                        array[i] = (i & 1) != 0;
                    }
                }

                @Override
                public boolean[] copy(boolean[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(
                        boolean[] expected, int expectedIndex, boolean[] actual, int actualIndex) {

                    if (expected[expectedIndex] != actual[actualIndex]) {
                        throw new AssertionError(
                                "expected=" + expected[expectedIndex] + ", actual=" + actual[actualIndex]);
                    }
                }
            },
            array -> BooleanIlaFactoryFromArray.create(array).create(),
            BooleanIla::length,
            BooleanIla::get);

    private static final IlaFuzzHarness<boolean[], BooleanIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private BooleanIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
