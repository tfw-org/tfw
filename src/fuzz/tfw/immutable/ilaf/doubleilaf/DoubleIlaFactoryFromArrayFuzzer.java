package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<double[], DoubleIla> SPEC = new IlaFuzzSpec<>(
            "DoubleIlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public double[] create(int length) {
                    return new double[length];
                }

                @Override
                public void initialize(double[] array) {
                    for (int i = 0; i < array.length; i++) {
                        switch (i & 7) {
                            case 0:
                                array[i] = 0.0;
                                break;
                            case 1:
                                array[i] = -0.0;
                                break;
                            case 2:
                                array[i] = Double.NaN;
                                break;
                            case 3:
                                array[i] = Double.POSITIVE_INFINITY;
                                break;
                            case 4:
                                array[i] = Double.NEGATIVE_INFINITY;
                                break;
                            case 5:
                                array[i] = Double.MIN_VALUE;
                                break;
                            case 6:
                                array[i] = Double.MAX_VALUE;
                                break;
                            default:
                                array[i] = i * 1.23456789;
                                break;
                        }
                    }
                }

                @Override
                public double[] copy(double[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(
                        double[] expected, int expectedIndex, double[] actual, int actualIndex) {
                    long expectedBits = Double.doubleToRawLongBits(expected[expectedIndex]);
                    long actualBits = Double.doubleToRawLongBits(actual[actualIndex]);
                    if (expectedBits != actualBits) {
                        throw new AssertionError("expectedBits="
                                + Long.toHexString(expectedBits)
                                + ", actualBits=" + Long.toHexString(actualBits));
                    }
                }
            },
            array -> DoubleIlaFactoryFromArray.create(array).create(),
            DoubleIla::length,
            DoubleIla::get);

    private static final IlaFuzzHarness<double[], DoubleIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private DoubleIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
