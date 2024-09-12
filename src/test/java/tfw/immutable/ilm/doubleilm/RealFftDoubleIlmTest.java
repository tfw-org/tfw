package tfw.immutable.ilm.doubleilm;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class RealFftDoubleIlmTest {
    @Test
    void testRealFftDoubleIlm() throws Exception {
        double[] input = new double[32];

        for (int i = 0; i < input.length; i++) {
            input[i] = Math.cos((double) i / (double) input.length * 2 * Math.PI);
        }

        DoubleIla inputIla = DoubleIlaFromArray.create(input);
        DoubleIlm inputIlm = ReplicateDoubleIlm.create(inputIla, 1);
        DoubleIlm outputIlm = RealFftDoubleIlm.create(inputIlm, (int) inputIlm.width());
        double[] output = DoubleIlmUtil.toArray(outputIlm);

        double[] checkReal = input.clone();
        double[] checkImag = new double[checkReal.length];
        PoskanzerFft fft = new PoskanzerFft(5);
        fft.doFft(checkReal, checkImag, false);

        // Looks like more work is needed to be able to "assert" something.
    }
}
