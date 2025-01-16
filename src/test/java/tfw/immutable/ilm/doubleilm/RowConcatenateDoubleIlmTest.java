package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class RowConcatenateDoubleIlmTest {
    @Test
    void rowConcatenateDoubleIlmTest() throws Exception {
        DoubleIla leftIla = DoubleIlaFromArray.create(new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        DoubleIlm leftIlm = TakeSkipDoubleIlm.create(leftIla, 5, 1);

        DoubleIla rightIla =
                DoubleIlaFromArray.create(new double[] {10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0});
        DoubleIlm rightIlm = TakeSkipDoubleIlm.create(rightIla, 5, 1);

        DoubleIlm concatenateIlm = RowConcatenateDoubleIlm.create(leftIlm, rightIlm);
        StridedDoubleIlm stridedConcatenateIlm = StridedDoubleIlmFromDoubleIlm.create(concatenateIlm, new double[0]);

        double[] test1Check = new double[] {
            0.0, 1.0, 2.0, 3.0, 4.0, 10.0, 11.0, 12.0, 13.0, 14.0,
            1.0, 2.0, 3.0, 4.0, 5.0, 11.0, 12.0, 13.0, 14.0, 15.0,
            2.0, 3.0, 4.0, 5.0, 6.0, 12.0, 13.0, 14.0, 15.0, 16.0,
            3.0, 4.0, 5.0, 6.0, 7.0, 13.0, 14.0, 15.0, 16.0, 17.0,
            4.0, 5.0, 6.0, 7.0, 8.0, 14.0, 15.0, 16.0, 17.0, 18.0
        };
        double[] test1Array = DoubleIlmUtil.toArray(concatenateIlm);
        assertThat(test1Check).isEqualTo(test1Array);

        double[] test2Check = new double[] {
            2.0, 3.0, 4.0, 0.0, 0.0,
            3.0, 4.0, 5.0, 0.0, 0.0,
            4.0, 5.0, 6.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
        };
        double[] test2Array = new double[25];
        stridedConcatenateIlm.get(test2Array, 0, 5, 1, 1, 1, 3, 3);
        assertThat(test2Check).isEqualTo(test2Array);

        double[] test3Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 5.0, 11.0, 12.0, 0.0,
            0.0, 6.0, 12.0, 13.0, 0.0,
            0.0, 7.0, 13.0, 14.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
        };
        double[] test3Array = new double[25];
        stridedConcatenateIlm.get(test3Array, 6, 5, 1, 1, 4, 3, 3);
        assertThat(test3Check).isEqualTo(test3Array);

        double[] test4Check = new double[] {
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 12.0, 0.0, 13.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 13.0, 0.0, 14.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
        };
        double[] test4Array = new double[25];
        stridedConcatenateIlm.get(test4Array, 6, 10, 2, 1, 6, 2, 2);
        assertThat(test4Check).isEqualTo(test4Array);
    }
}
