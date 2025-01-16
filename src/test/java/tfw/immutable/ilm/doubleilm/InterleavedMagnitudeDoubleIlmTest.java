package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class InterleavedMagnitudeDoubleIlmTest {
    @Test
    void interleavedMagnitudeDoubleIlmTest() throws Exception {
        DoubleIla doubleIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        DoubleIlm doubleIlm = TakeSkipDoubleIlm.create(doubleIla, 6, 1);
        DoubleIlm magnitude = InterleavedMagnitudeDoubleIlm.create(doubleIlm);
        StridedDoubleIlm stridedMagnitude = StridedDoubleIlmFromDoubleIlm.create(magnitude, new double[0]);

        double[] test1Check = new double[] {1.0, 13.0, 41.0, 5.0, 25.0, 61.0, 13.0, 41.0, 85.0, 25.0, 61.0, 113.0};
        double[] test1Array = DoubleIlmUtil.toArray(magnitude);
        assertThat(test1Check).isEqualTo(test1Array);

        double[] test2Check = new double[] {41.0, 85.0, 0.0, 61.0, 113.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] test2Array = new double[12];
        stridedMagnitude.get(test2Array, 0, 3, 1, 2, 1, 2, 2);
        assertThat(test2Check).isEqualTo(test2Array);

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0, 1.0, 13.0,
            0.0, 5.0, 25.0
        };
        double[] test3Array = new double[12];
        stridedMagnitude.get(test3Array, 7, 3, 1, 0, 0, 2, 2);
        assertThat(test3Check).isEqualTo(test3Array);

        double[] test4Check = new double[] {0.0, 0.0, 0.0, 25.0, 0.0, 61.0, 0.0, 0.0, 0.0, 41.0, 0.0, 85.0};
        double[] test4Array = new double[12];
        stridedMagnitude.get(test4Array, 3, 6, 2, 1, 1, 2, 2);
        assertThat(test4Check).isEqualTo(test4Array);
    }
}
