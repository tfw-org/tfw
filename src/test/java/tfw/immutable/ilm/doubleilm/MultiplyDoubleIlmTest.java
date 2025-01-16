package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class MultiplyDoubleIlmTest {
    @Test
    void multiplyDoubleIlmTest() throws Exception {
        DoubleIla leftIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        DoubleIlm leftIlm = TakeSkipDoubleIlm.create(leftIla, 5, 1);

        DoubleIla rightIla = DoubleIlaFromArray.create(new double[] {9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0});
        DoubleIlm rightIlm = TakeSkipDoubleIlm.create(rightIla, 5, 1);

        DoubleIlm multiplyIlm = MultiplyDoubleIlm.create(leftIlm, rightIlm);
        StridedDoubleIlm stridedMultiplyIlm = StridedDoubleIlmFromDoubleIlm.create(multiplyIlm, new double[0]);

        double[] test1Check = new double[] {
            0.0, 8.0, 14.0, 18.0, 20.0, 8.0, 14.0, 18.0, 20.0, 20.0, 14.0, 18.0, 20.0, 20.0, 18.0, 18.0, 20.0, 20.0,
            18.0, 14.0, 20.0, 20.0, 18.0, 14.0, 8.0
        };
        double[] test1Array = DoubleIlmUtil.toArray(multiplyIlm);
        assertThat(test1Check).isEqualTo(test1Array);

        double[] test2Check = new double[] {
            20.0, 20.0, 18.0, 0.0, 0.0, 20.0, 18.0, 14.0, 0.0, 0.0, 18.0, 14.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test2Array = new double[25];
        stridedMultiplyIlm.get(test2Array, 0, 5, 1, 2, 2, 3, 3);
        assertThat(test2Check).isEqualTo(test2Array);

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 8.0, 14.0,
            0.0, 0.0, 8.0, 14.0, 18.0,
            0.0, 0.0, 14.0, 18.0, 20.0
        };
        double[] test3Array = new double[25];
        stridedMultiplyIlm.get(test3Array, 12, 5, 1, 0, 0, 3, 3);
        assertThat(test3Check).isEqualTo(test3Array);

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 14.0, 0.0, 18.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 18.0, 0.0, 20.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0
        };
        double[] test4Array = new double[25];
        stridedMultiplyIlm.get(test4Array, 6, 10, 2, 1, 1, 2, 2);
        assertThat(test4Check).isEqualTo(test4Array);
    }
}
