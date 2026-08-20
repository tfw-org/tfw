package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<int[], IntIla> SPEC = new IlaFuzzSpec<>(
            "IntIlaFactoryFromArray",
            new IlaArrayAdapter<>() {

                @Override
                public int[] create(int length) {
                    return new int[length];
                }

                @Override
                public void initialize(int[] array) {

                    for (int i = 0; i < array.length; i++) {
                        array[i] = i * 0x9e3779b9 ^ 0x12345678;
                    }
                }

                @Override
                public int[] copy(int[] array) {

                    return array.clone();
                }

                @Override
                public void assertElementEquals(int[] expected, int expectedIndex, int[] actual, int actualIndex) {

                    if (expected[expectedIndex] != actual[actualIndex]) {
                        throw new AssertionError(
                                "expected=" + expected[expectedIndex] + ", actual=" + actual[actualIndex]);
                    }
                }
            },
            array -> IntIlaFactoryFromArray.create(array).create(),
            IntIla::length,
            IntIla::get);

    private static final IlaFuzzHarness<int[], IntIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private IntIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
