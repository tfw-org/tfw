package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<float[], FloatIla> SPEC = new IlaFuzzSpec<>(
            "FloatIlaFactoryFromArray",
            new IlaArrayAdapter<>() {
                @Override
                public float[] create(int length) {
                    return new float[length];
                }

                @Override
                public void initialize(float[] array) {
                    for (int i = 0; i < array.length; i++) {
                        switch (i & 7) {
                            case 0:
                                array[i] = 0.0f;
                                break;
                            case 1:
                                array[i] = -0.0f;
                                break;
                            case 2:
                                array[i] = Float.NaN;
                                break;
                            case 3:
                                array[i] = Float.POSITIVE_INFINITY;
                                break;
                            case 4:
                                array[i] = Float.NEGATIVE_INFINITY;
                                break;
                            case 5:
                                array[i] = Float.MIN_VALUE;
                                break;
                            case 6:
                                array[i] = Float.MAX_VALUE;
                                break;
                            default:
                                array[i] = i * 1.2345678f;
                                break;
                        }
                    }
                }

                @Override
                public float[] copy(float[] array) {
                    return array.clone();
                }

                @Override
                public void assertElementEquals(float[] expected, int expectedIndex, float[] actual, int actualIndex) {
                    int expectedBits = Float.floatToRawIntBits(expected[expectedIndex]);
                    int actualBits = Float.floatToRawIntBits(actual[actualIndex]);
                    if (expectedBits != actualBits) {
                        throw new AssertionError("expectedBits="
                                + Integer.toHexString(expectedBits)
                                + ", actualBits=" + Integer.toHexString(actualBits));
                    }
                }
            },
            array -> FloatIlaFactoryFromArray.create(array).create(),
            FloatIla::length,
            FloatIla::get);

    private static final IlaFuzzHarness<float[], FloatIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private FloatIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
// AUTO GENERATED FROM TEMPLATE
