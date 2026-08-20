package tfw.immutable.ilaf.booleanilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            boolean[],
            BooleanIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.booleanSpec());

    private BooleanIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
