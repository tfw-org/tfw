package tfw.immutable.ilaf.objectilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            String[],
            ObjectIla<String>> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.objectSpec());

    private ObjectIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
