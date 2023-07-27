package tfw.immutable.ilm.doubleilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class InterleavedMagnitudeDoubleIlmTest {
    @Test
    void testInterleavedMagnitudeDoubleIlm() throws Exception {
        DoubleIla doubleIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        DoubleIlm doubleIlm = TakeSkipDoubleIlm.create(doubleIla, 6, 1);
        DoubleIlm magnitude = InterleavedMagnitudeDoubleIlm.create(doubleIlm);

        double[] test1Check = new double[] {1.0, 13.0, 41.0, 5.0, 25.0, 61.0, 13.0, 41.0, 85.0, 25.0, 61.0, 113.0};
        double[] test1Array = DoubleIlmUtil.toArray(magnitude);
        assertTrue(Arrays.equals(test1Check, test1Array));

        double[] test2Check = new double[] {41.0, 85.0, 0.0, 61.0, 113.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] test2Array = new double[12];
        magnitude.toArray(test2Array, 0, 3, 1, 2, 1, 2, 2);
        assertTrue(Arrays.equals(test2Check, test2Array));

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0, 1.0, 13.0,
            0.0, 5.0, 25.0
        };
        double[] test3Array = new double[12];
        magnitude.toArray(test3Array, 7, 3, 1, 0, 0, 2, 2);
        assertTrue(Arrays.equals(test3Check, test3Array));

        double[] test4Check = new double[] {0.0, 0.0, 0.0, 25.0, 0.0, 61.0, 0.0, 0.0, 0.0, 41.0, 0.0, 85.0};
        double[] test4Array = new double[12];
        magnitude.toArray(test4Array, 3, 6, 2, 1, 1, 2, 2);
        assertTrue(Arrays.equals(test4Check, test4Array));
    }
}
