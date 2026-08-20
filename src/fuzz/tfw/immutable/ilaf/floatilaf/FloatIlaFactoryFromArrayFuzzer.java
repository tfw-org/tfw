package tfw.immutable.ilaf.floatilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            float[],
            FloatIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.floatSpec());

    private FloatIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
