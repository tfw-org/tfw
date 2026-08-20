package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.charila.CharIla;

public final class CharIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            char[],
            CharIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.charSpec());

    private CharIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
