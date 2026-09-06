package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.longila.LongIla;

public final class LongIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<long[], LongIla> SPEC = new IlaFuzzSpec<>(
            "LongIlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public long[] create(int length) {
                    return new long[length];
                }

                @Override
                public void initialize(long[] array) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = 0x123456789ABCDEFL ^ ((long) i * 0x100000001L);
                    }
                }

                @Override
                public long[] copy(long[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(long[] expected, int expectedIndex, long[] actual, int actualIndex) {
                    if (expected[expectedIndex] != actual[actualIndex]) {
                        throw new AssertionError(
                                "expected=" + expected[expectedIndex] + ", actual=" + actual[actualIndex]);
                    }
                }
            },
            array -> LongIlaFactoryFromArray.create(array).create(),
            LongIla::length,
            LongIla::get);

    private static final IlaFuzzHarness<long[], LongIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private LongIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
// AUTO GENERATED FROM TEMPLATE
