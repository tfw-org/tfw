package tfw.immutable.ilaf.byteilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpecs;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIlaFactoryFromArrayFuzzer {

    private static final IlaFuzzHarness<
            byte[],
            ByteIla> HARNESS =
            new IlaFuzzHarness<>(
                    IlaFuzzSpecs.byteSpec());

    private ByteIlaFactoryFromArrayFuzzer() {
    }

    public static void fuzzerTestOneInput(
            FuzzedDataProvider data)
            throws Exception {

        HARNESS.fuzz(data);
    }
}
