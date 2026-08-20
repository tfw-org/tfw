package tfw.immutable.ilaf.intilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.intila.IntIla;

public final class IntIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            int[],
            IntIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.intSpec());

    private IntIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
