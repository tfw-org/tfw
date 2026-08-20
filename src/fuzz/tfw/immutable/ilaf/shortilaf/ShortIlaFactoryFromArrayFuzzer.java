package tfw.immutable.ilaf.shortilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            short[],
            ShortIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.shortSpec());

    private ShortIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
