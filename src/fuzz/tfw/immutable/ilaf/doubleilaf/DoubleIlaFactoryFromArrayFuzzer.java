package tfw.immutable.ilaf.doubleilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            double[],
            DoubleIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.doubleSpec());

    private DoubleIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
