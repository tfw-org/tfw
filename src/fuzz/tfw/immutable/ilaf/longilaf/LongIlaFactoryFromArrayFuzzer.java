package tfw.immutable.ilaf.longilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.longila.LongIla;

public final class LongIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            long[],
            LongIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.longSpec());

    private LongIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
