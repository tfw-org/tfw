package tfw.immutable.ilaf.charilaf;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import tfw.fuzz.IlaArrayAdapter;
import tfw.fuzz.IlaFuzzHarness;
import tfw.fuzz.IlaFuzzSpec;
import tfw.immutable.ila.charila.CharIla;

public final class CharIlaFactoryFromArrayFuzzer {
    private static final IlaFuzzSpec<char[], CharIla> SPEC = new IlaFuzzSpec<>(
            "CharIlaFactoryFromArray",
            new IlaArrayAdapter<>() {

                @Override
                public char[] create(int length) {
                    return new char[length];
                }

                @Override
                public void initialize(char[] array) {

                    for (int i = 0; i < array.length; i++) {

                        switch (i & 3) {
                            case 0:
                                array[i] = '\0';
                                break;

                            case 1:
                                array[i] = '\uffff';
                                break;

                            case 2:
                                array[i] = (char) i;
                                break;

                            default:
                                array[i] = (char) (0xffff - i);
                                break;
                        }
                    }
                }

                @Override
                public char[] copy(char[] array) {

                    return array.clone();
                }

                @Override
                public void assertElementEquals(char[] expected, int expectedIndex, char[] actual, int actualIndex) {

                    if (expected[expectedIndex] != actual[actualIndex]) {
                        throw new AssertionError(
                                "expected=" + (int) expected[expectedIndex] + ", actual=" + (int) actual[actualIndex]);
                    }
                }
            },
            array -> CharIlaFactoryFromArray.create(array).create(),
            CharIla::length,
            CharIla::get);

    private static final IlaFuzzHarness<char[], CharIla> HARNESS = new IlaFuzzHarness<>(SPEC);

    private CharIlaFactoryFromArrayFuzzer() {}

    public static void fuzzerTestOneInput(FuzzedDataProvider data) throws Exception {
        HARNESS.fuzz(data);
    }
}
