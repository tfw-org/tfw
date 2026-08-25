package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<byte[], ByteIla> SPEC = new IlaFuzzSpec<>(
            "ByteIlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public byte[] create(int length) {
                    return new byte[length];
                }

                @Override
                public void initialize(byte[] array) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = (byte) (i * 37 + 11);
                    }
                }

                @Override
                public byte[] copy(byte[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(
                        byte[] expected, int expectedIndex, byte[] actual, int actualIndex) {
                    if (expected[expectedIndex] != actual[actualIndex]) {
                        throw new AssertionError(
                            "expected=" + expected[expectedIndex] + ", actual=" + actual[actualIndex]);
                    }
                }
            },
            array -> ByteIlaFactoryFromArray.create(array).create(),
            ByteIla::length,
            ByteIla::get);

    private static final IlaFuzzHarness<byte[], ByteIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private ByteIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
