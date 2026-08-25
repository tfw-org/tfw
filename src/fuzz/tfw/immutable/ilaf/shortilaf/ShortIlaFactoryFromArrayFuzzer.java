package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<short[], ShortIla> SPEC = new IlaFuzzSpec<>(
                    "ShortIlaFactoryFromArray",
                    new IlaArrayAdapter<>() {
                        @Override
                        public short[] create(int length) {
                            return new short[length];
                        }

                        @Override
                        public void initialize(short[] array) {
for (int i = 0; i < array.length; i++) {
    array[i] = (short) (i * 7919 + 12345);
}
                        }

                        @Override
                        public short[] copy(short[] array) {
                            return array.clone();
                        }

                        @Override
                        public void assertElementEquals(
                                short[] expected, int expectedIndex, short[] actual, int actualIndex) {
if (expected[expectedIndex] != actual[actualIndex]) {
    throw new AssertionError(
        "expected=" + expected[expectedIndex] + ", actual=" + actual[actualIndex]);
}
                        }
                    },
                    array -> ShortIlaFactoryFromArray.create(array).create(),
                    ShortIla::length,
                    ShortIla::get);

    private static final IlaFuzzHarness<short[], ShortIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ShortIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
